package com.audit_ledger.audit_writer.domain.repository;

import com.audit_ledger.audit_writer.domain.model.LogAuditModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditWriterRepository extends JpaRepository<LogAuditModel, Long> {
    @Query("select log from LogAuditModel log order by log.createdAt DESC LIMIT 1")
    Optional<LogAuditModel> findLastLog();
}
