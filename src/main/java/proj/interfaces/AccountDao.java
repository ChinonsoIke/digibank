package proj.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.entities.Account;

public interface AccountDao extends JpaRepository<Account, Long> {
    public Account findByNumber(String accountNumber);
}
