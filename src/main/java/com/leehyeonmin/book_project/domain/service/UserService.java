package com.leehyeonmin.book_project.domain.service;

import com.leehyeonmin.book_project.domain.dto.UserDto;

import java.util.List;

public interface UserService {
    public List<UserDto> findAllUser();

    public UserDto findUser(Long id);

    public UserDto addUser(UserDto userDto);

    public UserDto modifyUser(UserDto userDto);

    public Boolean removeUser(Long id);
}
