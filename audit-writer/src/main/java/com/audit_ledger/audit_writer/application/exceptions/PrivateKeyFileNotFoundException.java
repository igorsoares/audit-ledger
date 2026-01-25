package com.audit_ledger.audit_writer.application.exceptions;

public class PrivateKeyFileNotFoundException extends RuntimeException{
    public PrivateKeyFileNotFoundException(String path) {
        super("The given path ("+path+") does not exist");
    }
}
