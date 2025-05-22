package proj.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import proj.dtos.ApiResponse;
import proj.dtos.ApiResponseBasic;
import proj.dtos.RegisterDto;
import proj.entities.User;
import proj.interfaces.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponseBasic register(@RequestBody @Valid RegisterDto request){
        return userService.register(request);
    }
}
