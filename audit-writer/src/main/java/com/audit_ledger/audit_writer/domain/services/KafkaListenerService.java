package com.audit_ledger.audit_writer.domain.services;

import com.audit_ledger.audit_writer.application.dtos.RecvEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {
    private final AuditWriterService auditWriterService;

    public KafkaListenerService(AuditWriterService auditWriterService) {
        this.auditWriterService = auditWriterService;
    }

    @KafkaListener(topics = "${kafka.topics.log-audit}")
    public void listenerAudit(RecvEvent record){
        auditWriterService.audit(record);
    }

}
