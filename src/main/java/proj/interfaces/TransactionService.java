package proj.interfaces;

import proj.dtos.*;
import proj.entities.Transaction;

import java.util.List;

public interface TransactionService {
    ApiResponseBasic deposit(DepositDto request);
    ApiResponseBasic withdraw(WithdrawDto request);
    ApiResponseBasic transfer(TransferDto request);
    ApiResponse<String> analyzeSpending(Long userId);
    ApiResponse<List<Transaction>> history(Long userId);
    // get transaction history
}
