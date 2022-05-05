package com.leehyeonmin.book_project.domain.serviceImpl;

import com.leehyeonmin.book_project.domain.User;
import com.leehyeonmin.book_project.domain.dto.UserDto;
import com.leehyeonmin.book_project.domain.service.UserService;
import com.leehyeonmin.book_project.domain.util.ToEntity;
import com.leehyeonmin.book_project.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<UserDto> findAllUser() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto findUser(Long id) {
        return new UserDto(userRepository.getById(id));
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User entity = ToEntity.from(userDto);
        UserDto saved = new UserDto(userRepository.save(entity));
        return saved;
    }

    @Override
    public UserDto modifyUser(UserDto userDto) {
        User entity = ToEntity.from(userDto);
        UserDto saved = new UserDto(userRepository.save(entity));
        return saved;
    }

    @Override
    public Boolean removeUser(Long id) {
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
            return true;
        } else return false;
    }


}
