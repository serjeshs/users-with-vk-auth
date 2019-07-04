package com.serjeshs.usersvk.dto;

import com.serjeshs.usersvk.domain.User;

public class UsersControllDto {

    private String description;
    private String name;
    private String password;
    private String role;

    public UsersControllDto(){

    }

    public UsersControllDto(String description, String name, String password, String role){
        this.description = description;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public static UsersControllDto toDto(User user){
        return new UsersControllDto(
                user.getDescription(),
                user.getName(),
                user.getPassword(),
                user.getRole()
        );
    }
}
