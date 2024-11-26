package ch.bbcag.ticketshop.security;

import java.util.Objects;

public class SignUpResponseDTO {

    private final Integer userId;
    private final String email;

    public SignUpResponseDTO(Integer userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        SignUpResponseDTO that = (SignUpResponseDTO) obj;
        return Objects.equals(this.userId, that.userId) && Objects.equals(this.email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email);
    }

    public Integer getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

}
