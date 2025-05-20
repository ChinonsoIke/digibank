package proj.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proj.dtos.ApiResponse;
import proj.entities.Report;
import proj.entities.Transaction;
import proj.entities.Account;
import proj.enums.TransactionType;
import proj.interfaces.ReportDao;
import proj.interfaces.ReportService;
import proj.interfaces.TransactionDao;
import proj.interfaces.AccountDao;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {
    private final ReportDao reportDao;
    private final TransactionDao transactionDao;
    private final AccountDao accountDao;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReportServiceImpl(ReportDao reportDao, TransactionDao transactionDao, AccountDao accountDao) {
        this.reportDao = reportDao;
        this.transactionDao = transactionDao;
        this.accountDao = accountDao;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public ApiResponse<Report> generateTransactionSummary(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            List<Transaction> transactions = transactionDao.findAll().stream()
                .filter(t -> t.getCreatedAt().isAfter(startDate) && t.getCreatedAt().isBefore(endDate))
                .collect(Collectors.toList());

            Map<String, Object> summary = new HashMap<>();
            summary.put("totalTransactions", transactions.size());
            summary.put("totalDeposits", transactions.stream().filter(t -> t.getType() == TransactionType.CREDIT).count());
            summary.put("totalWithdrawals", transactions.stream().filter(t -> t.getType() == TransactionType.DEBIT).count());
            summary.put("transactions", transactions);

            Report report = new Report();
            report.setType("TRANSACTION_SUMMARY");
            report.setUserId(userId);
            report.setContent(objectMapper.writeValueAsString(summary));
            report = reportDao.save(report);

            return new ApiResponse<>(report, "Transaction summary report generated successfully", "00");
        } catch (Exception e) {
            return new ApiResponse<>(null, "Error generating transaction summary: " + e.getMessage(), "99");
        }
    }

    @Override
    public ApiResponse<Report> generateAccountStatement(Long accountId, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            Optional<Account> accountOpt = accountDao.findById(accountId);
            if (accountOpt.isEmpty()) {
                return new ApiResponse<>(null, "Account not found", "99");
            }

            Account account = accountOpt.get();
            List<Transaction> transactions = account.getTransactions().stream()
                .filter(t -> t.getCreatedAt().isAfter(startDate) && t.getCreatedAt().isBefore(endDate))
                .collect(Collectors.toList());

            Map<String, Object> statement = new HashMap<>();
            statement.put("accountNumber", account.getNumber());
            statement.put("accountType", account.getType());
            statement.put("currentBalance", account.getBalance());
            statement.put("startDate", startDate);
            statement.put("endDate", endDate);
            statement.put("transactions", transactions);

            Report report = new Report();
            report.setType("ACCOUNT_STATEMENT");
            report.setUserId(account.getUser().getId());
            report.setContent(objectMapper.writeValueAsString(statement));
            report = reportDao.save(report);

            return new ApiResponse<>(report, "Account statement generated successfully", "00");
        } catch (Exception e) {
            return new ApiResponse<>(null, "Error generating account statement: " + e.getMessage(), "99");
        }
    }

    @Override
    public ApiResponse<Report> generateUserActivity(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            List<Account> userAccounts = accountDao.findAll().stream()
                .filter(a -> a.getUser().getId().equals(userId))
                .collect(Collectors.toList());

            List<Transaction> allTransactions = new ArrayList<>();
            for (Account account : userAccounts) {
                allTransactions.addAll(account.getTransactions().stream()
                    .filter(t -> t.getCreatedAt().isAfter(startDate) && t.getCreatedAt().isBefore(endDate))
                    .collect(Collectors.toList()));
            }

            Map<String, Object> activity = new HashMap<>();
            activity.put("userId", userId);
            activity.put("totalAccounts", userAccounts.size());
            activity.put("totalTransactions", allTransactions.size());
            activity.put("accounts", userAccounts);
            activity.put("transactions", allTransactions);

            Report report = new Report();
            report.setType("USER_ACTIVITY");
            report.setUserId(userId);
            report.setContent(objectMapper.writeValueAsString(activity));
            report = reportDao.save(report);

            return new ApiResponse<>(report, "User activity report generated successfully", "00");
        } catch (Exception e) {
            return new ApiResponse<>(null, "Error generating user activity report: " + e.getMessage(), "99");
        }
    }

    @Override
    public ApiResponse<List<Report>> getUserReports(Long userId) {
        try {
            List<Report> reports = reportDao.findByUserId(userId);
            return new ApiResponse<>(reports, "User reports retrieved successfully", "00");
        } catch (Exception e) {
            return new ApiResponse<>(null, "Error retrieving user reports: " + e.getMessage(), "99");
        }
    }

    @Override
    public ApiResponse<List<Report>> getUserReportsByType(Long userId, String type) {
        try {
            List<Report> reports = reportDao.findByUserIdAndType(userId, type);
            return new ApiResponse<>(reports, "User reports of type " + type + " retrieved successfully", "00");
        } catch (Exception e) {
            return new ApiResponse<>(null, "Error retrieving user reports: " + e.getMessage(), "99");
        }
    }
} 