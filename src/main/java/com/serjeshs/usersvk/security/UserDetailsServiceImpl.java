package com.serjeshs.usersvk.security;

import com.serjeshs.usersvk.domain.User;
import com.serjeshs.usersvk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByName(name);
        UserBuilder builder = null;
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            builder = org.springframework.security.core.userdetails.User.withUsername(name);
            builder.password(user.getPassword());
            builder.roles(user.getRole());
        } else {
            throw new UsernameNotFoundException("User " + name + " not found");
        }
        return builder.build();
    }

//    @PostConstruct
//    public void init() {
//        User user = new User();
//        user.setName("user");
//        user.setPassword(new BCryptPasswordEncoder().encode("123"));
//        user.setRole(User.ROLE_ADMIN);
//        userRepository.save(user);
//    }

}
