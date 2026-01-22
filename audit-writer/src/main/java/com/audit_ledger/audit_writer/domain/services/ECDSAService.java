package com.audit_ledger.audit_writer.domain.services;

import com.audit_ledger.audit_writer.application.common.AuditCommon;
import com.audit_ledger.audit_writer.application.exceptions.KeyPairLoadException;
import com.audit_ledger.audit_writer.application.exceptions.PrivateKeyFileNotFoundException;
import com.audit_ledger.audit_writer.application.interfaces.SignMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

@Component
public class ECDSAService implements SignMessage {
    @Value("${ecdsa.private-key-file-location}")
    private String privateKeyPath;

    private static final Logger log = LoggerFactory.getLogger(ECDSAService.class);

    private PrivateKey loadKeyPair() {
        try{
            if(this.privateKeyPath != null){
                return loadPrivateKey();
            }
            return generatePrivateKey();
        }catch (Exception e){
            log.error("Error (loadKeyPair) : {}",e.getMessage());
            throw e;
        }
    }

    private PrivateKey generatePrivateKey(){
        try{
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            keyGen.initialize(256);

            KeyPair keyPair = keyGen.generateKeyPair();
            return keyPair.getPrivate();
        }catch (Exception e){
            throw new KeyPairLoadException(e.getMessage());
        }
    }

    private PrivateKey loadPrivateKey() {
        File privateFile = new File(this.privateKeyPath);
        if(!privateFile.exists()){
            throw new PrivateKeyFileNotFoundException(this.privateKeyPath);
        }
        StringBuilder privateKeyStrBuilder = new StringBuilder();
        try(Scanner reader = new Scanner(privateFile)){
            while(reader.hasNextLine()){
                var line = reader.nextLine();
                System.out.println(line);
                privateKeyStrBuilder.append(line).append("\n");
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
            System.out.println("Private key has been loaded");
            return privateKey;
        } catch (Exception e){
            throw new KeyPairLoadException(e.getMessage());
        }
    }

    @Override
    public String sign(String message) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        PrivateKey privateKey = loadKeyPair();
        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initSign(privateKey);

        signature.update(message.getBytes(StandardCharsets.UTF_8));

        return AuditCommon.bytesToHex(signature.sign());
    }
}
