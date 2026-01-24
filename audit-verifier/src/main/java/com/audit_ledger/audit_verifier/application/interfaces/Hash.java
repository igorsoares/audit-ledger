package com.audit_ledger.audit_verifier.application.interfaces;

/**
 * This contract is responsible for set the method
 * for hashing a payload. (SHA256, MD5, SHA-3...)
 */
public interface Hash {
    /**
     * A hash contract which receives a rawtext (payload) and returns a hash
     * in hexadecimal format.
     */
    String hash(String payload);
}
