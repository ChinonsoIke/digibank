package proj.interfaces;

import proj.dtos.*;

public interface TransactionService {
    ApiResponseBasic deposit(DepositDto request);
    ApiResponseBasic withdraw(WithdrawDto request);
    ApiResponseBasic transfer(TransferDto request);
    ApiResponse<String> analyzeSpending(Long userId);
    // get transaction history
}
