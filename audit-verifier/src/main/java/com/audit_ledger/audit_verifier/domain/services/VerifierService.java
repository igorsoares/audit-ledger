package com.audit_ledger.audit_verifier.domain.services;

import com.audit_ledger.audit_verifier.domain.model.LogAuditModel;
import com.audit_ledger.audit_verifier.domain.repository.AuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;

@Service
public class VerifierService implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(VerifierService.class);
    private final AuditRepository auditRepository;
    private final PublicKeyProviderService publicKeyProviderService;

    public VerifierService(AuditRepository auditRepository, PublicKeyProviderService publicKeyProviderService) {
        this.auditRepository = auditRepository;
        this.publicKeyProviderService = publicKeyProviderService;
    }

    public void verify(){
        //todo
        // 1- check if a pubkey is configured
        // 2- retrieve all data
        // 2- in a loop, check:
        //  2.1 - whether the current hash matches (recalculate)
        //  2.2 - verify the signature with de pkey
        //  2.3 - proceed to another "block"
        PublicKey publicKey = publicKeyProviderService.getPublicKey();
        logger.info("Retrieving all logs");
        List<LogAuditModel> allLogs = auditRepository.findAll();

        for(LogAuditModel log : allLogs){
            String currentHash = log.getCurrentHash();

        }


    }

    private Boolean isHashCorrect(LogAuditModel log){
        String hash = log.getCurrentHash();


    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.verify();
    }
}
