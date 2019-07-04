package com.serjeshs.usersvk.service;

import com.serjeshs.usersvk.domain.User;
import com.serjeshs.usersvk.dto.UsersControllDto;

public interface UsersService {

    User addUser(UsersControllDto usersControllDto);

    void deleteUser(UsersControllDto usersControllDto);

    User changePassword(UsersControllDto usersControllDto);
}
