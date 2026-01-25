package com.audit_ledger.audit_writer.domain.services;

import com.audit_ledger.audit_writer.application.common.ECDSAProperties;
import com.audit_ledger.audit_writer.application.exceptions.KeyPairLoadException;
import com.audit_ledger.audit_writer.application.interfaces.LoadPrivateKeyFile;
import com.audit_ledger.audit_writer.application.interfaces.SignMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

@Component
public class ECDSAService implements SignMessage {
    private final LoadPrivateKeyFile loadPrivateKey;
    private final ECDSAProperties ecdsaProperties;
    private static final Logger log = LoggerFactory.getLogger(ECDSAService.class);
    private PrivateKey privateKey;

    public ECDSAService(LoadPrivateKeyFile loadPrivateKey, ECDSAProperties ecdsaProperties) {
        this.loadPrivateKey = loadPrivateKey;
        this.ecdsaProperties = ecdsaProperties;
    }

    private PrivateKey loadKeyPair() {
        try{
            if(!ObjectUtils.isEmpty(ecdsaProperties.getPrivateKeyFileLocation())){
                return loadPrivateKey.load();
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
