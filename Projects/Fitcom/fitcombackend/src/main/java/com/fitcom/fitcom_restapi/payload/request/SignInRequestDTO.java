package com.fitcom.fitcom_restapi.payload.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class SignInRequestDTO {
    @NotBlank(message = "Principal can not be empty!")
    private String principal;

    @NotBlank(message = "Password can not be empty!")
    private String password;

    // Constructors
    public SignInRequestDTO(String principal, String password) {
        this.principal = principal;
        this.password = password;
    }

    public SignInRequestDTO() {
    }

    // Getter
    public String getPrincipal() {
        return principal;
    }
    public String getPassword() {
        return password;
    }

    // Setter
    public void setPrincipal(String principal) {
        this.principal = principal;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // Equals & HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignInRequestDTO that = (SignInRequestDTO) o;
        return Objects.equals(principal, that.principal) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(principal, password);
    }
}
