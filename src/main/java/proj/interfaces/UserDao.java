package proj.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.entities.User;

public interface UserDao extends JpaRepository<User, Long> {
    public User findUserByEmail(String email);
}
