package com.audit_ledger.audit_verifier.domain.services;

import com.audit_ledger.audit_verifier.application.exceptions.InvalidPayloadHashException;
import com.audit_ledger.audit_verifier.application.interfaces.Hash;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class SHA256Service implements Hash {
    @Override
    public String hash(String payload) {
        if(ObjectUtils.isEmpty(payload))
            throw new InvalidPayloadHashException(payload);
        return "";
    }
}
