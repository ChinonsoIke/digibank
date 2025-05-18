package proj.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.entities.Transaction;

public interface TransactionDao extends JpaRepository<Transaction, Long> {
}
