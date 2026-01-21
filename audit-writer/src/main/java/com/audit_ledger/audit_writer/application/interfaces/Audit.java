package com.audit_ledger.audit_writer.application.interfaces;

import com.audit_ledger.audit_writer.application.dtos.RecvEvent;

public interface Audit {
    void audit(RecvEvent event);
}
