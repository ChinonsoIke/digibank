package proj.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import proj.dtos.*;
import proj.interfaces.TransactionService;
import proj.utils.JwtService;
import io.jsonwebtoken.Claims;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private HttpServletRequest request;

    private String extractTokenFromRequest() {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new RuntimeException("Invalid or missing Authorization header");
    }

    private Claims validateAndGetClaims() {
        String token = extractTokenFromRequest();
        return jwtService.extractAllClaims(token);
    }

    @PostMapping("/transactions/deposit")
    public ApiResponseBasic deposit(@RequestBody DepositDto request) {
        Claims claims = validateAndGetClaims();
        request.userId = Long.parseLong(claims.getSubject());
        return transactionService.deposit(request);
    }

    @PostMapping("/transactions/withdraw")
    public ApiResponseBasic withdraw(@RequestBody WithdrawDto request) {
        Claims claims = validateAndGetClaims();
        request.userId = Long.parseLong(claims.getSubject());
        return transactionService.withdraw(request);
    }

    @PostMapping("/transactions/transfer")
    public ApiResponseBasic transfer(@RequestBody TransferDto request) {
        Claims claims = validateAndGetClaims();
        request.userId = Long.parseLong(claims.getSubject());
        return transactionService.transfer(request);
    }

    @GetMapping("/transactions/analyze")
    public ApiResponse<String> analyzeSpending(){
        Claims claims = validateAndGetClaims();
        return transactionService.analyzeSpending(Long.parseLong(claims.getSubject()));
    }
}
