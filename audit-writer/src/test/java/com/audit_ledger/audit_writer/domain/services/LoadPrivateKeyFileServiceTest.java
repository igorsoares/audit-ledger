package com.audit_ledger.audit_writer.domain.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.security.PrivateKey;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "ecdsa.private-key-file-location=src/test/resources/keys/private-test.pem"
})
class LoadPrivateKeyFileServiceTest {

    @Autowired
    private LoadPrivateKeyFileService loadPrivateKeyFileService;

    @Test
    @DisplayName("Should load private key successfully.")
    void load_successfully() {
        String pkcs8Format = "PKCS#8";
        String algo = "EC";

        PrivateKey privateKey = loadPrivateKeyFileService.load();

        Assertions.assertThat(privateKey).isNotNull();
        Assertions.assertThat(privateKey.getFormat()).isEqualTo(pkcs8Format);
        Assertions.assertThat(privateKey.getAlgorithm()).isEqualTo(algo);
    }
}