package com.fitcom.fitcom_restapi.model;

public enum ERole {
    ROLE_USER,
    ROLE_MODERATOR,
    ROLE_ADMIN;

    @Override
    public String toString() {
        return switch (this) {
            case ROLE_ADMIN -> "ADMIN";
            case ROLE_USER -> "USER";
            case ROLE_MODERATOR -> "MODERATOR";
            default -> throw new IllegalArgumentException("Unknown role: " + this);
        };
    }
}
