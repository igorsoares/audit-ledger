package com.audit_ledger.audit_writer.domain.services;

import com.audit_ledger.audit_writer.application.exceptions.KeyPairLoadException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "ecdsa.private-key-file-location=src/test/resources/keys/private-test-invalid.pem"
})
class LoadPrivateKeyInvalidPemServiceTest {

    @Autowired
    private LoadPrivateKeyFileService loadPrivateKeyFileService;

    @Test
    @DisplayName("Load private key. Should throw KeyPairLoadException due to invalid private key format")
    void load_shouldThrowBase64Exception() {
        Assertions.assertThatThrownBy( () -> loadPrivateKeyFileService.load())
                        .isInstanceOf(KeyPairLoadException.class);
    }
}