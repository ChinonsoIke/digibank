package proj.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import proj.dtos.ApiResponse;
import proj.dtos.ApiResponseBasic;
import proj.dtos.RegisterDto;
import proj.entities.User;
import proj.interfaces.AccountService;
import proj.interfaces.UserDao;
import proj.interfaces.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder pEncoder;

    @Override
    public ApiResponseBasic register(RegisterDto request) {
        if(!request.password.equals(request.confirmPassword)) return new ApiResponseBasic("Passwords do not match", "99");
        User user = new User(request.firstName, request.lastName, request.email, pEncoder.encode(request.password));
        user = userDao.save(user);
        accountService.addAccount(user, request.accountType);
        return new ApiResponseBasic("User registered successfully", "200");
    }

    @Override
    public User getUserById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    public User getUserByEmail(String email){
        return userDao.findUserByEmail(email);
    }
}
