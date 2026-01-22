package com.audit_ledger.audit_writer.application.exceptions;

public class KeyPairLoadException extends RuntimeException{
    public KeyPairLoadException(String message) {
        super("Failed to load or generate a key pair : "+message);
    }
}
