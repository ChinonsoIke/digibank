package proj.dtos;

import proj.entities.Account;
import proj.enums.AccountType;
import proj.enums.TransactionType;

public class RegisterDto {
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String confirmPassword;
    public AccountType accountType;
}
