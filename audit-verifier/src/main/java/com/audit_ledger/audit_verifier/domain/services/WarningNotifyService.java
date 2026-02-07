package com.audit_ledger.audit_verifier.domain.services;

import com.audit_ledger.audit_verifier.application.interfaces.PanicButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WarningNotifyService implements PanicButton {
    private Logger log = LoggerFactory.getLogger(WarningNotifyService.class);
    @Override
    public void execute() {
        log.warn("Unauthorized modification detected. Sending an email to the system administrator...");
    }
}
