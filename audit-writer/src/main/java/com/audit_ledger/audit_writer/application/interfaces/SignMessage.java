package com.audit_ledger.audit_writer.application.interfaces;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface SignMessage {
    String sign(String message) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException;
}
