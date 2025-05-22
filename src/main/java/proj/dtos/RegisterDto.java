package proj.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import proj.enums.AccountType;

public class RegisterDto {
    @NotBlank
    public String firstName;
    @NotBlank
    public String lastName;
    @Email
    public String email;
    public String password;
    public String confirmPassword;
    public AccountType accountType;
}
