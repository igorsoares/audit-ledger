package com.audit_ledger.audit_writer.domain.services;

import com.audit_ledger.audit_writer.application.exceptions.KeyPairLoadException;
import com.audit_ledger.audit_writer.application.exceptions.PrivateKeyFileNotFoundException;
import com.audit_ledger.audit_writer.application.interfaces.LoadPrivateKeyFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

@Component
public class LoadPrivateKeyFileService implements LoadPrivateKeyFile {
    @Value("${ecdsa.private-key-file-location}")
    private String privateKeyPath;

    private static final Logger log = LoggerFactory.getLogger(LoadPrivateKeyFileService.class);

    @Override
    public PrivateKey load() {
        File privateFile = new File(this.privateKeyPath);
        if(!privateFile.exists()){
            throw new PrivateKeyFileNotFoundException(this.privateKeyPath);
        }
        StringBuilder privateKeyStrBuilder = new StringBuilder();
        try(Scanner reader = new Scanner(privateFile)){
            while(reader.hasNextLine()){
                privateKeyStrBuilder.append(reader.nextLine()).append("\n");
            }

            String formattedPrivateKey = privateKeyStrBuilder.toString()
                    .replace("-----BEGIN PRIVATE KEY-----","")
                    .replace("-----END PRIVATE KEY-----","")
                    .replace(" ","")
                    .replace("\n","");

            byte[] keyBytes = Base64.getDecoder().decode(formattedPrivateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("EC");

            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            log.info("Private key has been loaded from local file");
            return privateKey;
        } catch (Exception e){
            throw new KeyPairLoadException(e.getMessage());
        }
    }
}
