package com.audit_ledger.audit_verifier.application.exceptions;

public class PublicKeyLoadException extends RuntimeException{
    public PublicKeyLoadException() {
        super("Please check your public key load configuration and try again");
    }

    public PublicKeyLoadException(String message) {
        super(message);
    }
}
