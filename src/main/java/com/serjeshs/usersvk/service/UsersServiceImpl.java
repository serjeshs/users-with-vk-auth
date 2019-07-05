package com.serjeshs.usersvk.service;

import com.serjeshs.usersvk.domain.User;
import com.serjeshs.usersvk.dto.UserDto;
import com.serjeshs.usersvk.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Optional;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {

    private final DataSource dataSource;
    private final UserRepository userRepository;

    public UsersServiceImpl(DataSource dataSource, UserRepository userRepository) {
        this.dataSource = dataSource;
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(UserDto userDto) {
        User user = new User(
                userDto.getName(),
                userDto.getDescription(),
                new BCryptPasswordEncoder().encode(userDto.getPassword()),
                userDto.getRole()
        );
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByName(userDto.getName());
        userOptional.ifPresent(userRepository::delete);
    }

    @Override
    public User changePassword(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByName(userDto.getName());
        User usersControll = null;
        if (userOptional.isPresent()) {
            usersControll =  userOptional.get();
            usersControll.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
            userRepository.save(usersControll);
        }
        return usersControll;
    }
}
