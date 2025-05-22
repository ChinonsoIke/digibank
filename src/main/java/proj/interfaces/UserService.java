package proj.interfaces;

import proj.dtos.ApiResponse;
import proj.dtos.ApiResponseBasic;
import proj.dtos.RegisterDto;
import proj.entities.User;

public interface UserService {
    ApiResponseBasic register(RegisterDto request);
    User getUserById(Long id);
    User getUserByEmail(String email);
}
