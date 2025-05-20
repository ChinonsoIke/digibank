package proj.interfaces;

import proj.dtos.ApiResponse;
import proj.entities.Report;
import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {
    ApiResponse<Report> generateTransactionSummary(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    ApiResponse<Report> generateAccountStatement(Long accountId, LocalDateTime startDate, LocalDateTime endDate);
    ApiResponse<Report> generateUserActivity(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    ApiResponse<List<Report>> getUserReports(Long userId);
    ApiResponse<List<Report>> getUserReportsByType(Long userId, String type);
} 