package com.audit_ledger.audit_verifier.domain.services;

import com.audit_ledger.audit_verifier.application.exceptions.VerifierException;
import com.audit_ledger.audit_verifier.application.interfaces.Hash;
import com.audit_ledger.audit_verifier.application.interfaces.PublicKeyProvider;
import com.audit_ledger.audit_verifier.application.interfaces.PanicButton;
import com.audit_ledger.audit_verifier.domain.model.LogAuditModel;
import com.audit_ledger.audit_verifier.domain.repository.AuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

@Service
public class VerifierService implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(VerifierService.class);
    private final AuditRepository auditRepository;
    private final PublicKeyProvider publicKeyProvider;
    private final Hash hashProvider;
    private final PanicButton panicButton;

    public VerifierService(AuditRepository auditRepository, PublicKeyProvider publicKeyProvider, Hash hashProvider, PanicButton panicButton) {
        this.auditRepository = auditRepository;
        this.publicKeyProvider = publicKeyProvider;
        this.hashProvider = hashProvider;
        this.panicButton = panicButton;
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void verify(){
        PublicKey publicKey = publicKeyProvider.getPublicKey();
        List<LogAuditModel> allLogs = auditRepository.findAll();
        allLogs = allLogs.stream().sorted(Comparator.comparing(LogAuditModel::getCreatedAt).reversed()).toList();

        for(LogAuditModel log : allLogs){
            if(!isHashTrustable(log)){
                logger.warn("The hash of log audit ID {} has been modified.",log.getCdId());
                panicButton.execute();
                return;
            }

            if(!isSignatureTrustable(log, publicKey)){
                logger.warn("The signature of log {} has been modified. Taking the necessary actions.",log.getCdId());
                panicButton.execute();
                return;
            }

        }
        logger.info("The audit has been successfully completed. No adulteration found.");
    }

    /**
     * Check if the 'signature' attribute is trustable. It should validate if the
     * signature was not adulterated.
     * @param log register
     * @return if the signature is validated
     */
    private boolean isSignatureTrustable(LogAuditModel log, PublicKey publicKey){
        try{
            byte[] signature = Base64.getDecoder().decode(log.getSignature());
            byte[] signedData = log.getCurrentHash().getBytes(StandardCharsets.UTF_8);
            Signature sig = Signature.getInstance("SHA256withECDSA");
            sig.initVerify(publicKey);
            sig.update(signedData);
            return sig.verify(signature);
        } catch (NoSuchAlgorithmException e) {
            throw new VerifierException("The given algorithm is invalid: "+e.getMessage());
        } catch (InvalidKeyException e) {
            throw new VerifierException("Invalid public key: "+e.getMessage());
        } catch (SignatureException e) {
            throw new VerifierException("Something went wrong: "+e.getMessage());
        }

    }

    private Boolean isHashTrustable(LogAuditModel log){
        String currentHash = log.getCurrentHash();
        String hashTest = this.hashProvider.hash(log.toString());

        return currentHash.equals(hashTest);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.verify();
    }
}
