package proj.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import proj.dtos.ApiResponse;
import proj.entities.Report;
import proj.interfaces.ReportService;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/transactions/{userId}")
    public ApiResponse<Report> generateTransactionSummary(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return reportService.generateTransactionSummary(userId, startDate, endDate);
    }

    @GetMapping("/account/{accountId}/statement")
    public ApiResponse<Report> generateAccountStatement(
            @PathVariable Long accountId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return reportService.generateAccountStatement(accountId, startDate, endDate);
    }

    @GetMapping("/user/{userId}/activity")
    public ApiResponse<Report> generateUserActivity(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return reportService.generateUserActivity(userId, startDate, endDate);
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<Report>> getUserReports(@PathVariable Long userId) {
        return reportService.getUserReports(userId);
    }

    @GetMapping("/user/{userId}/type/{type}")
    public ApiResponse<List<Report>> getUserReportsByType(
            @PathVariable Long userId,
            @PathVariable String type) {
        return reportService.getUserReportsByType(userId, type);
    }
} 