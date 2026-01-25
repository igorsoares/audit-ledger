# audit-ledger

<div align="center">
  <img width="400" height="400" alt="gemini-2 5-flash-image_Instead_of_black_paint_in_green-0" src="https://github.com/user-attachments/assets/adce1a0d-8cd5-40c1-aef1-47291b6032fa" />
  <p><strong>Don't Trust. Verify !</strong></p>
</div>



<div align="center">

  [Installation](#installation) ·
  [Design](#design) ·
  
</div>




## Description

An append-only audit logging system that uses cryptographic hash chains and ECDSA signatures to provide verifiable integrity and tamper detection for distributed systems.

## Stack
This project uses the following stack:

- Java 21
- Maven
- Docker && Docker-compose
- Spring Boot framework
- Apache Kafka
- PostgreSQL
- Flyway

## Installation

1- Clone this repository

```bash
git clone git@github.com:igorsoares/audit-ledger.git
```

2- Docker compose run

```bash
cd audit-ledger && docker-compose up -d
```

### Configuration

To run this project correctly, you will need an ECDSA key pair.

On Unix-based systems, you can generate it using the following commands:

1- Generate a private key

```bash
openssl ecparam -name prime256v1 -genkey -noout -out ecdsa_01.pem
```

This file will be used in the audit-verifier project to verify log signatures.

**Please ensure that the private key file (ecdsa_01.pem) is stored securely.**

2- Generate a public key

```bash
openssl ec -in ecdsa_01.pem -pubout -out ec_public.pem
```

This file will be used in the `audit-verifier` project to verify the signature of logs.

## Design

<img width="1842" height="1071" alt="image" src="https://github.com/user-attachments/assets/01424433-480b-442e-98d3-b02debb4caba" />

This software is designed to work with two microsservices. 

### Why hash chains and digital signatures ?
Hash chains ensure **event ordering and historical integrity**, since every record is linked to the previous one through a cryptographic hash.

<img width="1285" height="547" alt="image" src="https://github.com/user-attachments/assets/b5b2856a-ef11-4e4d-b220-4d9dd716e31b" />

In the example above, there are four audit log records.  

Now imagine that someone tries to tamper with **log 02**, changing the information to claim that it was not Alice who requested a new payment method, but Bob.

After this unauthorized modification, the hash of the **log 02** record would change completely, breaking the entire chain (**log 03**, **log 04**, and so on), since each record depends on the hash of the previous one.

The digital signature (ECDSA) ensure authenticity and non-repudiation.

**Even if an attacker recalculates the entire hash chain, they would still need the private key to forge the logs.**

As long as the private key remains secure, any unauthorized modification of the audit logs will be detected.

### Audit Writer
The first one, `audit-writer` is responsible for receiving all the events from Kafka and process them.



#### What is this process ?

The audit writer is going to valid all input events and do all the math problems, such as Hashing (SHA256 is being used) and the digital signing (ECDSA)

### Audit verifier
The second one, `audit-verifier` is responsible for verifying all the log chain

