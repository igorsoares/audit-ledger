package com.audit_ledger.audit_writer.domain.services;

import com.audit_ledger.audit_writer.application.exceptions.KeyPairLoadException;
import com.audit_ledger.audit_writer.application.exceptions.PrivateKeyFileNotFoundException;
import com.audit_ledger.audit_writer.application.interfaces.SignMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

@Component
public class ECDSAService implements SignMessage {
    private PrivateKey privateKey;
    @Value("${ecdsa.private-key-file-location}")
    private String privateKeyPath;

    private static final Logger log = LoggerFactory.getLogger(ECDSAService.class);

    private PrivateKey loadKeyPair() {
        try{
            if(!ObjectUtils.isEmpty(privateKeyPath)){
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
            log.info("No file found for private key. Generating a key pair...");
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            keyGen.initialize(256);

            KeyPair keyPair = keyGen.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            String privateAsBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            String publicAsBase64 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            log.info("""
            The keypair was successfully generated :
            \n
            -----BEGIN PRIVATE KEY-----
            {}
            -----END PRIVATE KEY-----
            
             -----BEGIN PUBLIC KEY-----
            {}
            -----END PUBLIC KEY-----
            \n
            """, privateAsBase64, publicAsBase64 );
            return privateKey;
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
            log.info("Private key has been loaded");
            return privateKey;
        } catch (Exception e){
            throw new KeyPairLoadException(e.getMessage());
        }
    }

    @Override
    public String sign(String message) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        if(this.privateKey == null){
            this.privateKey = loadKeyPair();
        }
        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initSign(privateKey);

        signature.update(message.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(signature.sign());
    }
}
