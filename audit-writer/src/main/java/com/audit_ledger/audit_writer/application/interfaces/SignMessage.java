package com.audit_ledger.audit_writer.application.interfaces;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface SignMessage {
    /**
     * A method that receives a plaintext and returns a signature encoded in Base64.
     * @param message A raw text
     * @return A base64 signature
     */
    String sign(String message) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException;
}
