package com.audit_ledger.audit_writer.application.dtos;

import com.audit_ledger.audit_writer.application.enums.EventType;

import java.time.OffsetDateTime;

public class RecvEvent{
    private Long userId;
    private EventType event;
    private String ipAddress;
    private OffsetDateTime timestamp;

    public RecvEvent() {
    }

    public RecvEvent(Long userId, EventType event, String ipAddress, OffsetDateTime timestamp) {
        this.userId = userId;
        this.event = event;
        this.ipAddress = ipAddress;
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public EventType getEvent() {
        return event;
    }

    public void setEvent(EventType event) {
        this.event = event;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
