package proj.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import proj.dtos.LoginRequest;
import proj.dtos.LoginResponse;
import proj.utils.JwtService;


@RestController
public class AuthController {
    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest user) {
        var userDetail = userDetailService.loadUserByUsername(user.email());
        var password = userDetail.getPassword();
        if (!passwordEncoder.matches(user.password(), password)) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String[] roleNames = userDetail.getAuthorities()
            .stream().map(a -> a.getAuthority().replace("ROLE_", "")).toArray(String[]::new);
        var token = jwtService.generateToken(userDetail.getUsername(), roleNames);
        var response = new LoginResponse(token); 
        return ResponseEntity.ok(response);
    }
}
