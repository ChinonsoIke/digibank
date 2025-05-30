package proj.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proj.dtos.*;
import proj.entities.Account;
import proj.entities.Transaction;
import proj.enums.TransactionType;
import proj.interfaces.AccountDao;
import proj.interfaces.NotificationService;
import proj.interfaces.TransactionDao;
import proj.interfaces.TransactionService;

import java.util.Optional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private NotificationService notificationService;

    public ApiResponseBasic deposit(DepositDto request){
        Optional<Account> accountOpt = accountDao.findById(request.accountId);
        if(accountOpt.isEmpty()) return new ApiResponseBasic("Account not found", "99");
        
        Account account = accountOpt.get();
        if(account.getUser().getId() != request.userId)  return new ApiResponseBasic("Something went wrong", "99");
        account.setBalance(account.getBalance() + request.amount);

        Transaction transaction = new Transaction(TransactionType.CREDIT, "User Deposit", account);
        transactionDao.save(transaction);
        
        // Send notification
        notificationService.notifyTransaction(
            request.userId,
            String.format("Deposit of $%.2f was successful to account %s", request.amount, account.getNumber())
        );
        
        return new ApiResponseBasic("Success", "00");
    }

    public ApiResponseBasic withdraw(WithdrawDto request){
        Optional<Account> accountOpt = accountDao.findById(request.accountId);
        if(accountOpt.isEmpty()) return new ApiResponseBasic("Account not found", "99");
        Account account = accountOpt.get();

        if(account.getUser().getId() != request.userId)  return new ApiResponseBasic("Something went wrong", "99");
        if(account.getBalance() < request.amount) return new ApiResponseBasic("Insufficient funds", "99");
        account.setBalance(account.getBalance() - request.amount);

        Transaction transaction = new Transaction(TransactionType.DEBIT, "User Withdrawal", account);
        transactionDao.save(transaction);
        
        // Send notification
        notificationService.notifyTransaction(
            request.userId,
            String.format("Withdrawal of $%.2f was successful from account %s", request.amount, account.getNumber())
        );
        
        return new ApiResponseBasic("Success", "00");
    }

    public ApiResponseBasic transfer(TransferDto request){
        Optional<Account> accountOpt = accountDao.findById(request.accountId);
        if(accountOpt.isEmpty()) return new ApiResponseBasic("Account not found", "99");
        Account account = accountOpt.get();

        if(account.getUser().getId() != request.userId)  return new ApiResponseBasic("Something went wrong", "99");
        if(account.getBalance() < request.amount) return new ApiResponseBasic("Insufficient funds", "99");

        Account recipient = accountDao.findByNumber(request.recipient);
        if(recipient == null)  return new ApiResponseBasic("Recipient account not found", "99");

        account.setBalance(account.getBalance() - request.amount);
        recipient.setBalance(recipient.getBalance() + request.amount);

        Transaction transaction1 = new Transaction(TransactionType.DEBIT, "Transfer Out", account);
        transactionDao.save(transaction1);
        Transaction transaction2 = new Transaction(TransactionType.CREDIT, "Transfer In", recipient);
        transactionDao.save(transaction2);

        // Send notifications to both sender and recipient
        notificationService.notifyTransaction(
            request.userId,
            String.format("Transfer of $%.2f was successful to account %s", request.amount, request.recipient)
        );
        
        notificationService.notifyTransaction(
            recipient.getUser().getId(),
            String.format("Received transfer of $%.2f from account %s", request.amount, account.getNumber())
        );

        return new ApiResponseBasic("Success", "00");
    }
}
