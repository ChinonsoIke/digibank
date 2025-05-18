package proj.interfaces;

import proj.entities.Account;
import proj.entities.Customer;
import proj.enums.AccountType;

public interface AccountService {
    Account addAccount(Customer customer, AccountType type);
}
