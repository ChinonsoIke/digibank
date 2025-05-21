package proj.interfaces;

import proj.dtos.ApiResponse;
import proj.dtos.RegisterDto;
import proj.entities.User;

public interface UserService {
    ApiResponse<User> register(RegisterDto request);
    User getUserById(Long id);
    User getUserByEmail(String email);
}
