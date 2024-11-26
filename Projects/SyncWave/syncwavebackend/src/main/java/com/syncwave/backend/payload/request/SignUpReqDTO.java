package com.syncwave.backend.payload.request;

import com.syncwave.backend.lib.validation.password.PasswordMatching;
import com.syncwave.backend.lib.validation.password.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static com.syncwave.backend.lib.constants.RegexConstants.EMAIL_REGEX;
import static com.syncwave.backend.lib.constants.RegexConstants.USERNAME_REGEX;

@Setter
@Getter
@PasswordMatching(
        password = "password",
        confirmPassword = "confirmPassword",
        message = "Password and Confirm Password don't match!"
)
public class SignUpReqDTO {
    @NotBlank(message = "Username is required!")
    @Pattern(regexp = USERNAME_REGEX, message = "Invalid username!")
    @Size(min = 3, max = 255, message = "Username must be between 3 and 255 characters!")
    private String username;

    @Email
    @Pattern(regexp = EMAIL_REGEX, message = "Invalid email!")
    @NotBlank(message = "Email is required!")
    @Size(min = 5, max = 255)
    private String email;

    @NotBlank(message = "Password is required!")
    @StrongPassword
    private String password;
    private String confirmPassword;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignUpReqDTO that = (SignUpReqDTO) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
