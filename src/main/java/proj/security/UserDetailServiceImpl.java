package proj.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import proj.entities.User;
import proj.interfaces.UserDao;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findUserByEmail(email);
        if(user == null) throw new UsernameNotFoundException("Username not found for " + email);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getId().toString())
                .password(user.getPassword())
                .roles(user.getIsAdmin() ? "ADMIN" : "USER")
                .build();
        return userDetails;
    }
}
