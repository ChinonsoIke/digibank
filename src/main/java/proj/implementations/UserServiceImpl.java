package proj.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proj.dtos.ApiResponse;
import proj.dtos.RegisterDto;
import proj.entities.Customer;
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

    @Override
    public ApiResponse<Customer> register(RegisterDto request) {
        Customer customer = new Customer(request.firstName, request.lastName, request.email, request.password);
        customer = userDao.save(customer);
        accountService.addAccount(customer, request.accountType);
        return new ApiResponse<>(customer, "User registered successfully", "200");
    }

    @Override
    public Customer getUserById(Long id) {
        return userDao.findById(id).orElse(null);
    }
}
