package com.fitcom.fitcom_restapi.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class SignUpRequestDTO {

    @Pattern(regexp = "/^[A-Za-z]\\w{1,256}$/gm" , message = "Invalid username!")
    @NotBlank(message = "Username can not be empty!")
    private String username;

    @Pattern(regexp = "/^(?!.*@.*@)(?!.*\\.\\.)(^[a-zA-Z0-9._%+-]{1,64}@)([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?:\\.[a-zA-Z]{2,256})?)$/gm" , message = "Invalid email!")
    @NotBlank(message = "Email can not be empty!")
    private String email;

    @Pattern(regexp = "/^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#\\$%\\^&\\*!]{8,}$/gm" , message = "Invalid password!")
    @NotBlank(message = "Password can not be empty!")
    private String password;

    private String role;

    // Constructors
    public SignUpRequestDTO(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public SignUpRequestDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public SignUpRequestDTO() {
    }

    // Getter
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setter
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Equals & HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignUpRequestDTO that = (SignUpRequestDTO) o;
        return Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password);
    }
}
