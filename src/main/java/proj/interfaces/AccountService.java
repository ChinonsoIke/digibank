package proj.interfaces;

import proj.entities.Account;
import proj.entities.User;
import proj.enums.AccountType;

public interface AccountService {
    Account addAccount(User user, AccountType type);
}
