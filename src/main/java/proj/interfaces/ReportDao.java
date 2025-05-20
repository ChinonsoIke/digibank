package proj.interfaces;

import proj.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportDao extends JpaRepository<Report, Long> {
    List<Report> findByUserId(Long userId);
    List<Report> findByUserIdAndType(Long userId, String type);
} 