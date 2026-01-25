package com.audit_ledger.audit_verifier.domain.services;

import com.audit_ledger.audit_verifier.application.exceptions.InvalidPayloadHashException;
import com.audit_ledger.audit_verifier.application.interfaces.Hash;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SHA256Service implements Hash {
    @Override
    public String hash(String payload) {
        try{
            if(ObjectUtils.isEmpty(payload))
                throw new InvalidPayloadHashException(payload);
            MessageDigest digest = MessageDigest.getInstance("SHA256");
            byte[] bData = digest.digest(payload.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(bData);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
