package com.audit_ledger.audit_writer.application.interfaces;

import com.audit_ledger.audit_writer.application.dtos.RecvEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface Audit {
    void audit(RecvEvent event) throws JsonProcessingException;
}
