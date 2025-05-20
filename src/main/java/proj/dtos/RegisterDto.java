package proj.dtos;

import proj.enums.AccountType;

public class RegisterDto {
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String confirmPassword;
    public AccountType accountType;
}
