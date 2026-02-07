package com.audit_ledger.audit_writer.domain.services;

import com.audit_ledger.audit_writer.application.common.ECDSAProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;


@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {
        "ecdsa.private-key-file-location=src/test/resources/keys/private-test.pem"
})
class ECDSAServiceTest {

    @InjectMocks
    private ECDSAService ecdsaService;

    @Mock
    private ECDSAProperties ecdsaProperties;

    @Test
    void sign() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        ecdsaService.sign("message");
    }
}