package com.serjeshs.usersvk.dto;

import com.serjeshs.usersvk.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String description;
    private String name;
    private String password;
    private String role;


    public UserDto(String description, String name, String password, String role){
        this.description = description;
        this.name = name;
        this.password = password;
        this.role = role;
    }


    public static UserDto toDto(User user){
        return new UserDto(
                user.getDescription(),
                user.getName(),
                user.getPassword(),
                user.getRole()
        );
    }
}
