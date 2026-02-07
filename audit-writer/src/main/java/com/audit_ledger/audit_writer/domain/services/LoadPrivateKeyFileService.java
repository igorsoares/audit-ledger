package com.audit_ledger.audit_writer.domain.services;

import com.audit_ledger.audit_writer.application.exceptions.KeyPairLoadException;
import com.audit_ledger.audit_writer.application.exceptions.PrivateKeyFileNotFoundException;
import com.audit_ledger.audit_writer.application.interfaces.LoadPrivateKeyFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

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
        try{
            String privateKeyStr = Files.readString(Path.of(this.privateKeyPath));

            privateKeyStr = privateKeyStr.replace("-----BEGIN PRIVATE KEY-----","")
                    .replace("-----END PRIVATE KEY-----","")
                    .replace(" ","")
                    .replace("\n","");

            byte[] keyBytes = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("EC");

            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            log.info("Private key has been loaded from local file");
            return privateKey;
        } catch (InvalidKeySpecException invalidKeyException){
            throw new KeyPairLoadException("Invalid private key");
        } catch (Exception e){
            throw new KeyPairLoadException(e.getMessage());
        }
    }
}
