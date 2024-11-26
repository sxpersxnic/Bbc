package ch.bbcag.ticketshop.person.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class PersonRequestDTO extends PersonResponseDTO {
    @Size(min = 8, message = "Password needs to be at least 8 characters long")
    @NotBlank(message = "Password can not be empty!")
    public String password;

    @Email(message = "Invalid email!", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    @NotBlank(message = "Email can not be empty!")
    private String email;

    // No args constructor
    public PersonRequestDTO() {
    }

    // Getter
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }

    // Setter
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonRequestDTO that = (PersonRequestDTO) o;
        return Objects.equals(password, that.password) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), password, email);
    }
}
