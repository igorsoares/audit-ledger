package com.audit_ledger.audit_writer.domain.services;

import com.audit_ledger.audit_writer.application.dtos.RecvEvent;
import com.audit_ledger.audit_writer.application.interfaces.Audit;
import com.audit_ledger.audit_writer.domain.repository.AuditWriterRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditWriterService implements Audit {
    private final AuditWriterRepository auditWriterRepository;

    public AuditWriterService(AuditWriterRepository auditWriterRepository) {
        this.auditWriterRepository = auditWriterRepository;
    }

    @Override
    public void audit(RecvEvent event) {

    }
}
