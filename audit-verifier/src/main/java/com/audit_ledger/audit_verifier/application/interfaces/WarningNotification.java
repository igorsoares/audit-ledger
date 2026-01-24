package com.audit_ledger.audit_verifier.application.interfaces;

/**
 * A contract designed to take action when the hash chain is broken
 * or a signature is tampered with.
 * Some examples: Notify the sysadmin via E-mail, SMS...
 */
public interface WarningNotification {
    void execute();
}
