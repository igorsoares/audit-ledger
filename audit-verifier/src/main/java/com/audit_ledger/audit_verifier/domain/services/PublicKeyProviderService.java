package com.audit_ledger.audit_verifier.domain.services;

import com.audit_ledger.audit_verifier.application.configuration.ECDSAProperties;
import com.audit_ledger.audit_verifier.application.exceptions.PublicKeyLoadException;
import com.audit_ledger.audit_verifier.application.interfaces.PublicKeyProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

@Service
public class PublicKeyProviderService implements PublicKeyProvider {
    private final ECDSAProperties ecdsaProperties;
    private PublicKey publicKey;
    private static final String KEY_FACTORY_ALGO = "ec"; // Elliptical Curve

    public PublicKeyProviderService(ECDSAProperties ecdsaProperties) {
        this.ecdsaProperties = ecdsaProperties;
    }

    @Override
    public PublicKey getPublicKey() throws PublicKeyLoadException {
        if(publicKey != null){
            return publicKey;
        }
        this.publicKey = loadPublicKeyFromFile();
        return this.publicKey;
    }

    private PublicKey loadPublicKeyFromFile() {
        try{
            File pubKeyPath = new File(ecdsaProperties.getPublicKeyFileLocation());
            if(!pubKeyPath.exists() || !pubKeyPath.canRead() || !pubKeyPath.isFile()){
                throw new PublicKeyLoadException("Error on reading the public key");
            }

            String pem = Files.readString(Path.of(ecdsaProperties.getPublicKeyFileLocation()));
            byte[] publicKeyFromBase64 = Base64.getDecoder().decode(cleanPKCS8Key(pem));
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyFromBase64);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGO);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new PublicKeyLoadException("Failed to load algorithm: "+KEY_FACTORY_ALGO+". "+e.getMessage());
        } catch (InvalidKeySpecException e) {
            throw new PublicKeyLoadException("Failed to generate keyspec: "+e.getMessage());
        } catch (IOException ioException){
            throw new PublicKeyLoadException("IOException: "+ioException.getMessage());
        }
    }

    private String cleanPKCS8Key(String publicKey){
        return publicKey.replace("-----BEGIN PUBLIC KEY-----","")
                .replace("-----END PUBLIC KEY-----","")
                .replace(" ","")
                .replace("\n","");
    }
}
