package com.audit_ledger.audit_writer.domain.model;

import com.audit_ledger.audit_writer.application.enums.EventType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
public class LogAuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long cdId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name="payload", nullable = false)
    private String payload;

    @Column(name="previous_hash", nullable = false)
    private String previousHash;

    @Column(name="current_hash", nullable = false)
    private String currentHash;

    @Column(name="signature", nullable = false)
    private String signature;

    @Column(name="signature_algo", nullable = false)
    private String signatureAlgo;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public LogAuditModel(Long userId, EventType eventType, String ipAddress, String payload, String previousHash, String signatureAlgo, LocalDateTime createdAt) {
        this.userId = userId;
        this.eventType = eventType;
        this.ipAddress = ipAddress;
        this.payload = payload;
        this.previousHash = previousHash;
        this.signatureAlgo = signatureAlgo;
        this.createdAt = createdAt;
    }

    public LogAuditModel() {
    }

    /**
     * This toString() method is used to generate a
     * SHA256 HASH
     */
    @Override
    public String toString() {
        return "LogAuditModel{" +
                "eventType=" + eventType +
                ", ipAddress='" + ipAddress + '\'' +
                ", payload='" + payload + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", signatureAlgo='" + signatureAlgo + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCdId() {
        return cdId;
    }

    public void setCdId(Long cdId) {
        this.cdId = cdId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getCurrentHash() {
        return currentHash;
    }

    public void setCurrentHash(String currentHash) {
        this.currentHash = currentHash;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignatureAlgo() {
        return signatureAlgo;
    }

    public void setSignatureAlgo(String signatureAlgo) {
        this.signatureAlgo = signatureAlgo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
