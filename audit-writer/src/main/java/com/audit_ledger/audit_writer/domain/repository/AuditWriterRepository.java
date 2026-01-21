package com.audit_ledger.audit_writer.domain.repository;

import com.audit_ledger.audit_writer.domain.model.LogAuditModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditWriterRepository extends JpaRepository<LogAuditModel, Long> {
}
