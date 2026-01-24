package com.audit_ledger.audit_verifier.application.interfaces;

import com.audit_ledger.audit_verifier.application.exceptions.PublicKeyLoadException;

import java.security.PublicKey;

/**
 * This contract is going to set how the public key
 * is being loaded. (AWS Bucket, local file, memory...)
 */
public interface PublicKeyProvider {
    /**
     * Loads a public key.
     *
     * @return the loaded public key
     * @throws PublicKeyLoadException if the key cannot be loaded
     */
    PublicKey getPublicKey() throws PublicKeyLoadException;
}
