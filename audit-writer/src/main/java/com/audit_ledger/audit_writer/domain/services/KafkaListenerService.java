package com.audit_ledger.audit_writer.domain.services;

import com.audit_ledger.audit_writer.application.dtos.RecvEvent;
import com.audit_ledger.audit_writer.application.interfaces.Audit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {
    private final Audit audit;
    private static final Logger log = LoggerFactory.getLogger(KafkaListenerService.class);

    public KafkaListenerService(Audit audit) {
        this.audit = audit;
    }

    @KafkaListener(topics = "${kafka.topics.log-audit}")
    public void listenerAudit(RecvEvent record){
        try{
            audit.audit(record);
        }catch (Exception exception){
            log.error("Error to process data : {}",exception.getMessage());
        }
    }

}
