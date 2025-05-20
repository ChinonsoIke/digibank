package proj.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proj.entities.Account;
import proj.entities.Customer;
import proj.enums.AccountType;
import proj.interfaces.AccountDao;
import proj.interfaces.AccountService;
import proj.interfaces.NotificationService;
import proj.utils.ToolBox;

import java.time.LocalDate;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;
    
    @Autowired
    private NotificationService notificationService;

    @Override
    public Account addAccount(Customer customer, AccountType type) {
        String accountNumber = ToolBox.generateAccountNumber();
        Account account = new Account(accountNumber, type, LocalDate.now(), customer);
        account = accountDao.save(account);

        // Send notification for new account creation
        notificationService.notifyAccountUpdate(
            customer.getId(),
            String.format("New %s account created successfully. Account number: %s", type, accountNumber)
        );

        return account;
    }
}
