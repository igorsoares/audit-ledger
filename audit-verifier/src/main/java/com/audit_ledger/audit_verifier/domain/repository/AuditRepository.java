package com.audit_ledger.audit_verifier.domain.repository;

import com.audit_ledger.audit_verifier.domain.model.LogAuditModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<LogAuditModel, Long> {

}
