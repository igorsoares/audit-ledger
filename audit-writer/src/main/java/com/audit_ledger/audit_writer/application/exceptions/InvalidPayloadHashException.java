package com.audit_ledger.audit_writer.application.exceptions;

public class InvalidPayloadHashException extends RuntimeException {
    public InvalidPayloadHashException(String message) {
        super("The given payload is invalid : "+message);
    }
}
