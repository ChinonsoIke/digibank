package proj.interfaces;

import proj.dtos.ApiResponseBasic;
import proj.dtos.DepositDto;
import proj.dtos.TransferDto;
import proj.dtos.WithdrawDto;

public interface TransactionService {
    ApiResponseBasic deposit(DepositDto request);
    ApiResponseBasic withdraw(WithdrawDto request);
    ApiResponseBasic transfer(TransferDto request);
    // get transaction history
}
