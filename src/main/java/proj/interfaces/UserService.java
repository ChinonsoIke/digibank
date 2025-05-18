package proj.interfaces;

import proj.dtos.ApiResponse;
import proj.dtos.RegisterDto;
import proj.entities.Customer;

public interface UserService {
    ApiResponse<Customer> register(RegisterDto request);
}
