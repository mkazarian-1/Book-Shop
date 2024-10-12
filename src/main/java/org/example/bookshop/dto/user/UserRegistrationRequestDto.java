package org.example.bookshop.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(max = 50)
    private String password;
    @NotBlank
    @Size(max = 50)
    private String repeatPassword;
    @NotBlank
    @Size(max = 30)
    private String firstName;
    @NotBlank
    @Size(max = 30)
    private String lastName;
    @Size(max = 200)
    private String shippingAddress;
}
