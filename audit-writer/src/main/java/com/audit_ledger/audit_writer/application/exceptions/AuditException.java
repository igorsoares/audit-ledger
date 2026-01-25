package com.audit_ledger.audit_writer.application.exceptions;

public class AuditException extends RuntimeException{
    public AuditException(String message) {
        super(message);
    }
}
