package com.serjeshs.usersvk.controller;

import com.serjeshs.usersvk.domain.User;
import com.serjeshs.usersvk.dto.UsersControllDto;
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

//    @GetMapping("/api/usersControll")
    @GetMapping("/api/users")
    public List<UsersControllDto> listUsers(Model model){

        List<User> usersControllDtoList = userRepository.findAll();
        return usersControllDtoList.stream().map(UsersControllDto::toDto).collect(Collectors.toList());
    }

//    @PostMapping("/api/usersControll")
    @PostMapping("/api/users")
    public User addUser(
            @RequestBody UsersControllDto usersControllDto
    ){
        return usersService.addUser(usersControllDto);
    }

//    @DeleteMapping("/api/usersControll")
    @DeleteMapping("/api/users")
    public void deleteUser(
            @RequestBody UsersControllDto usersControllDto
    ){
         usersService.deleteUser(usersControllDto);
    }

//    @PatchMapping("/api/usersControll")
    @PatchMapping("/api/users")
    public  User changePassword(
            @RequestBody UsersControllDto usersControllDto
    ){
         return usersService.changePassword(usersControllDto);
    }
}
