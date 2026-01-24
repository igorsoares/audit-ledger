package com.audit_ledger.audit_verifier.application.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ecdsa")
public class ECDSAProperties {
    private String publicKeyFileLocation;

    public ECDSAProperties() {
    }

    public String getPublicKeyFileLocation() {
        return publicKeyFileLocation;
    }

    public void setPublicKeyFileLocation(String publicKeyFileLocation) {
        this.publicKeyFileLocation = publicKeyFileLocation;
    }
}
