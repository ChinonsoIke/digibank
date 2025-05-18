package proj.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.entities.Customer;

public interface UserDao extends JpaRepository<Customer, Long> {
}
