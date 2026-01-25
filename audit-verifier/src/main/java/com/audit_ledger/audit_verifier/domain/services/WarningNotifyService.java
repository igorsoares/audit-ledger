package com.audit_ledger.audit_verifier.domain.services;

import com.audit_ledger.audit_verifier.application.interfaces.PanicButton;
import org.springframework.stereotype.Service;

@Service
public class WarningNotifyService implements PanicButton {
    @Override
    public void execute() {
        System.out.println("Something went really wrong.");
    }
}
