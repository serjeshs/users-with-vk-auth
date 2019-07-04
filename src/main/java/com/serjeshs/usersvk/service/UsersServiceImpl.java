package com.serjeshs.usersvk.service;

import com.serjeshs.usersvk.domain.User;
import com.serjeshs.usersvk.dto.UsersControllDto;
import com.serjeshs.usersvk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.util.Optional;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {

    private final DataSource dataSource;

    private final UserRepository userRepository;


    @Autowired
    public UsersServiceImpl(DataSource dataSource, UserRepository userRepository) {
        this.dataSource = dataSource;
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(UsersControllDto usersControllDto) {
        User user = new User(
                usersControllDto.getName(),
                usersControllDto.getDescription(),
                new BCryptPasswordEncoder().encode(usersControllDto.getPassword()),
                usersControllDto.getRole()
        );
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UsersControllDto usersControllDto) {
        Optional<User> userOptional = userRepository.findByName(usersControllDto.getName());
        userOptional.ifPresent(userRepository::delete);
    }

    @Override
    public User changePassword(UsersControllDto usersControllDto) {
        Optional<User> userOptional = userRepository.findByName(usersControllDto.getName());
        User usersControll = null;
        if (userOptional.isPresent()) {
            usersControll =  userOptional.get();
            usersControll.setPassword(new BCryptPasswordEncoder().encode(usersControllDto.getPassword()));
            userRepository.save(usersControll);
        }
        return usersControll;
    }
}
