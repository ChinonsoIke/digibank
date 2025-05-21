package proj.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import proj.entities.Transaction;

import java.util.List;

public interface TransactionDao extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t join t.account a where a.user.id = :userId")
    List<Transaction> getUserTransactions(Long userId);
}
