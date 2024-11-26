package com.fitcom.fitcom_restapi.payload.response;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class SignUpResponseDTO {
    private UUID uuid;
    private String username;
    private String email;
    private Instant createTime;
    private String role;

    //: Constructor
    public SignUpResponseDTO(UUID uuid, String username, String email, Instant createTime, String role) {
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.createTime = createTime;
        this.role = role;
    }
    public SignUpResponseDTO() {}

    //: Getter & Setter
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //: Equals & HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignUpResponseDTO that = (SignUpResponseDTO) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
