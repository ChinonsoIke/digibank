package proj.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import proj.dtos.ApiResponse;
import proj.dtos.RegisterDto;
import proj.entities.Customer;
import proj.interfaces.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ApiResponse<Customer> register(@RequestBody RegisterDto request){
        return userService.register(request);
    }
}
