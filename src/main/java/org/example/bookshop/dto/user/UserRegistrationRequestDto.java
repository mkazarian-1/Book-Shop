package org.example.bookshop.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.example.bookshop.dto.validator.FieldMatch;

@Getter
@Setter
@FieldMatch(first = "password", second = "repeatPassword",message = "Passwords do not match")
public class UserRegistrationRequestDto {
    @NotBlank
    @Pattern(regexp = "^[^@]+@[^@]+\\.[^@]+$")
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String shippingAddress;
}
