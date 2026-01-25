package com.audit_ledger.audit_verifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuditVerifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditVerifierApplication.class, args);
	}

}
