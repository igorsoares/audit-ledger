package com.audit_ledger.audit_verifier.application.exceptions;

public class VerifierException extends RuntimeException{
    public VerifierException() {
    }

    public VerifierException(String message) {
        super(message);
    }
}
