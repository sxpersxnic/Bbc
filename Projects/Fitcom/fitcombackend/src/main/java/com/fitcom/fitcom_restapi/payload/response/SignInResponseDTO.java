package com.fitcom.fitcom_restapi.payload.response;

import java.util.Objects;
import java.util.UUID;

public class SignInResponseDTO {
    private UUID uuid;
    private String jwtToken;
    private String username;
    private String email;
    private String role;

    //: Constructor
    public SignInResponseDTO(UUID uuid, String jwtToken, String username, String email, String role) {
        this.uuid = uuid;
        this.jwtToken = jwtToken;
        this.username = username;
        this.email = email;
        this.role = role;
    }
    public SignInResponseDTO() {}

    //: Getter & Setter
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
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
        SignInResponseDTO that = (SignInResponseDTO) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
