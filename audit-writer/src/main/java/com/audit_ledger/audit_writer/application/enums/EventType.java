package com.audit_ledger.audit_writer.application.enums;


public enum EventType {
    SYSTEM_CONFIG_CHANGED(Category.SYSTEM),
    RATE_LIMIT_UPDATED(Category.SYSTEM),
    API_KEY_CREATED(Category.SYSTEM),
    API_KEY_REVOKED(Category.SYSTEM),

    REFUND_REQUESTED(Category.FINANCIAL),
    REFUND_PROCESSED(Category.FINANCIAL),
    PAYMENT_METHOD_CHANGED(Category.FINANCIAL),
    PAYMENT_METHOD_ADDED(Category.FINANCIAL),
    PAYMENT_METHOD_REMOVED(Category.FINANCIAL),
    PAYMENT_ATTEMPT_FAILED(Category.FINANCIAL),

    ROLES_UPDATED(Category.USER),
    USER_LOGIN(Category.USER),
    USER_LOGIN_FAILED(Category.USER),
    USER_LOGOUT(Category.USER),
    PASSWORD_CHANGED(Category.USER),
    PASSWORD_RESET_REQUESTED(Category.USER),
    PASSWORD_RESET_COMPLETED(Category.USER),
    MFA_ENABLED(Category.USER),
    MFA_DISABLED(Category.USER),
    MFA_CHALLENGE_FAILED(Category.USER),
    EMAIL_CHANGED(Category.USER);

    private final Category category;

    EventType(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public enum Category {
        SYSTEM, FINANCIAL, USER
    }
}
