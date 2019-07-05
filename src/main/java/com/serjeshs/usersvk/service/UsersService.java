package com.serjeshs.usersvk.service;

import com.serjeshs.usersvk.domain.User;
import com.serjeshs.usersvk.dto.UserDto;

public interface UsersService {

    User addUser(UserDto userDto);

    void deleteUser(UserDto userDto);

    User changePassword(UserDto userDto);
}
