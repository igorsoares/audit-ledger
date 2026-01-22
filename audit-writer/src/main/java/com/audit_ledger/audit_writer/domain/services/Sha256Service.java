package com.audit_ledger.audit_writer.domain.services;

import com.audit_ledger.audit_writer.application.common.AuditCommon;
import com.audit_ledger.audit_writer.application.exceptions.InvalidPayloadHashException;
import com.audit_ledger.audit_writer.application.interfaces.Hash;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Sha256Service implements Hash {
    @Override
    public String execute(String payload) throws NoSuchAlgorithmException, InvalidPayloadHashException {
        if(ObjectUtils.isEmpty(payload))
            throw new InvalidPayloadHashException(payload);
        MessageDigest digest = MessageDigest.getInstance("SHA256");
        return AuditCommon.bytesToHex(digest.digest(payload.getBytes(StandardCharsets.UTF_8)));
    }



}
