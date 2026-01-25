package com.audit_ledger.audit_writer.application.interfaces;

import java.security.PrivateKey;

public interface LoadPrivateKeyFile {
    /**
     * A contract to load the private key, which can be multiple sources,
     * such as local file, in memory, AWS S3, etc...
     */
    PrivateKey load();
}
