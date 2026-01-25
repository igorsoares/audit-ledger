package com.audit_ledger.audit_writer.application.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ecdsa")
public class ECDSAProperties {
    private String privateKeyFileLocation;

    public String getPrivateKeyFileLocation() {
        return privateKeyFileLocation;
    }

    public void setPrivateKeyFileLocation(String privateKeyFileLocation) {
        this.privateKeyFileLocation = privateKeyFileLocation;
    }
}
