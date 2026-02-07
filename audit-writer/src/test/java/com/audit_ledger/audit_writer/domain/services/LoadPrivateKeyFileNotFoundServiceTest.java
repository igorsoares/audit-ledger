package com.audit_ledger.audit_writer.domain.services;

import com.audit_ledger.audit_writer.application.exceptions.PrivateKeyFileNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "ecdsa.private-key-file-location=src/test/resources/keys/invalid-private.pem"
})
class LoadPrivateKeyFileNotFoundServiceTest {

    @Autowired
    private LoadPrivateKeyFileService loadPrivateKeyFileService;

    @Test
    @DisplayName("Load private key. Should throw private key file not exception.")
    void load_shouldThrowPrivateKeyFileNotFoundException() {
        String message="The given path (src/test/resources/keys/invalid-private.pem) does not exist";
        Assertions.assertThatThrownBy( () -> loadPrivateKeyFileService.load())
                        .isInstanceOf(PrivateKeyFileNotFoundException.class)
                .hasMessage(message);
    }
}