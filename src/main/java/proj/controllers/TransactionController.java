package proj.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import proj.dtos.ApiResponseBasic;
import proj.dtos.DepositDto;
import proj.dtos.TransferDto;
import proj.dtos.WithdrawDto;
import proj.interfaces.TransactionService;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions/deposit")
    public ApiResponseBasic deposit(@RequestBody DepositDto request){
        return transactionService.deposit(request);
    }

    @PostMapping("/transactions/withdraw")
    public  ApiResponseBasic withdraw(@RequestBody WithdrawDto request){
        return transactionService.withdraw(request);
    }

    @PostMapping("/transactions/transfer")
    public ApiResponseBasic transfer(@RequestBody TransferDto request){
        return transactionService.transfer(request);
    }
}
