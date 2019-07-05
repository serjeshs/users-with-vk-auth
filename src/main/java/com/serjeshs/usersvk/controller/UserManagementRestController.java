package com.serjeshs.usersvk.controller;

import com.serjeshs.usersvk.domain.User;
import com.serjeshs.usersvk.dto.UserDto;
import com.serjeshs.usersvk.repository.UserRepository;
import com.serjeshs.usersvk.service.UsersService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserManagementRestController {

    private final UserRepository userRepository;
    private final UsersService usersService;

    public UserManagementRestController(UserRepository userRepository, UsersService usersService){
        this.userRepository = userRepository;
        this.usersService = usersService;
    }

    @GetMapping("/api/users")
    public List<UserDto> listUsers(Model model){
        List<User> usersControllDtoList = userRepository.findAll();
        return usersControllDtoList.stream().map(UserDto::toDto).collect(Collectors.toList());
    }

    @PostMapping("/api/users")
    public User addUser(
            @RequestBody UserDto userDto
    ){
        return usersService.addUser(userDto);
    }

    @DeleteMapping("/api/users")
    public void deleteUser(
            @RequestBody UserDto userDto
    ){
         usersService.deleteUser(userDto);
    }

    @PatchMapping("/api/users")
    public  User changePassword(
            @RequestBody UserDto userDto
    ){
         return usersService.changePassword(userDto);
    }
}
