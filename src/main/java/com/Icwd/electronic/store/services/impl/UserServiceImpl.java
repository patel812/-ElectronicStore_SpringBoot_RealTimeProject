package com.Icwd.electronic.store.services.impl;

import com.Icwd.electronic.store.dtos.UserDto;
import com.Icwd.electronic.store.entities.User;
import com.Icwd.electronic.store.repositories.UserRepository;
import com.Icwd.electronic.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
public class UserServiceImpl implements UserService {

    //To get object of Repository for interact with database
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDto createUser(UserDto userDto) {

        // generate unique id in string format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        // dto -> entity
        User user = dtoToEntity(userDto);
        User savedUser = userRepository.save(user);

        //entity -> dto
        UserDto newDto = entityToDto(savedUser);
        return newDto;
    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public List<UserDto> getAllUser() {
        return null;
    }

    @Override
    public UserDto getUserById(String userId) {
        return null;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        return null;
    }





    //Manually Mapping***********************************************************************
    //DTO to Entity method for User &
    // Entity to DTO method for user

    // Entity -> Dto (Convert method)
    private UserDto entityToDto(User savedUser) {

        UserDto userDto = UserDto.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .about(savedUser.getAbout())
                .gender(savedUser.getGender())
                .imageName(savedUser.getImageName())
                .build();

        return userDto;
    }

    // DTO -> Entity (Convert method)
    private User dtoToEntity(UserDto userDto) {

        User user = User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .about(userDto.getAbout())
                .gender(userDto.getGender())
                .imageName(userDto.getImageName())
                .build();

        return user;
    }



}
