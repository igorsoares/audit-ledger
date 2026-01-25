package com.audit_ledger.audit_writer.domain.services;

import com.audit_ledger.audit_writer.application.dtos.RecvEvent;
import com.audit_ledger.audit_writer.application.exceptions.AuditException;
import com.audit_ledger.audit_writer.application.interfaces.Audit;
import com.audit_ledger.audit_writer.application.interfaces.Hash;
import com.audit_ledger.audit_writer.application.interfaces.SignMessage;
import com.audit_ledger.audit_writer.domain.model.LogAuditModel;
import com.audit_ledger.audit_writer.domain.repository.AuditWriterRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuditWriterService implements Audit {
    private final AuditWriterRepository auditWriterRepository;
    private final Hash hashService;
    private final SignMessage signMessage;
    private final String SIGNATURE_ALGO = "ECDSA";
    private final String GENESIS_HASH = "0000";
    private final Logger log = LoggerFactory.getLogger(AuditWriterService.class);

    public AuditWriterService(AuditWriterRepository auditWriterRepository, Hash hashService, SignMessage signMessage) {
        this.auditWriterRepository = auditWriterRepository;
        this.hashService = hashService;
        this.signMessage = signMessage;
    }

    @Override
    public void audit(RecvEvent event) throws JsonProcessingException {
        try {
            log.info("Starting new audit event");
            String recvEventAsString = new ObjectMapper().findAndRegisterModules().writeValueAsString(event);
            LocalDateTime now = LocalDateTime.now();

            LogAuditModel newAudit = new LogAuditModel(
                    event.getUserId(), event.getEvent(), event.getIpAddress(),
                    recvEventAsString, getPreviousHash(),
                    SIGNATURE_ALGO);

            newAudit.setCurrentHash(hashService.execute(newAudit.toString()));
            newAudit.setSignature(signMessage.sign(newAudit.getCurrentHash()));

            auditWriterRepository.save(newAudit);
            log.info("The current log event has been successfully created");
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            log.error("Couldn't audit correctly due to : {}", e.getMessage());
            throw new AuditException(e.getMessage());
        }
    }

    private String getPreviousHash() {
        Optional<LogAuditModel> lastAuditLog = auditWriterRepository.findLastLog();
        if (lastAuditLog.isEmpty())
            return GENESIS_HASH;

        return lastAuditLog.get().getCurrentHash();
    }
}
