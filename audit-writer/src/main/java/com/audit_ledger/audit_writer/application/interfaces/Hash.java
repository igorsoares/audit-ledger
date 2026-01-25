package com.audit_ledger.audit_writer.application.interfaces;

import com.audit_ledger.audit_writer.application.exceptions.InvalidPayloadHashException;

import java.security.NoSuchAlgorithmException;

public interface Hash {
    String execute(String payload) throws NoSuchAlgorithmException, InvalidPayloadHashException;
}
