package com.Icwd.electronic.store.services.impl;

import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.dtos.UserDto;
import com.Icwd.electronic.store.entities.User;
import com.Icwd.electronic.store.exceptions.ResourceNotFoundException;
import com.Icwd.electronic.store.helper.Helper;
import com.Icwd.electronic.store.repositories.UserRepository;
import com.Icwd.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@Service
public class UserServiceImpl implements UserService {

    //To get object of Repository for interact with database
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;



    //Create User **********************************************************************************
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


    //Update User **********************************************************************************
    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        // 1. Find by id
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));

        // 2. Set update data & Get update data
        //user set all data,  UserDto have all data
        user.setName(userDto.getName());
        //email update
        //user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());


        // 3. Save update data
        //We are getting user entity in updateUser(user)
        User updatedUser = userRepository.save(user);

        // 4. Entity -> Dto
        UserDto updatedDto = entityToDto(updatedUser);

        return updatedDto;
    }


    //Delete User **********************************************************************************
    @Override
    public void deleteUser(String userId) {

       // 1. Find User by Id (From Entity)
       User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found by id"));

       // 2. Delete user (delete do not have return type)
       userRepository.delete(user);

    }



    //Get All User **********************************************************************************
    @Override                       // 1. Pagination                 2. Sorting
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

        //2. Sort
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());



        // 2. Pagination                                        sort object
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // 3. Pagination
        Page<User> page = userRepository.findAll(pageable);

        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);

        return response;
    }



    //Get User By Id **********************************************************************************
    @Override
    public UserDto getUserById(String userId) {

        // 1. Find by id of User
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found as per given Id!!"));

        // 2. Convert user entity to Dto & Return
        return entityToDto(user);
    }



    //Get User by Email Id **********************************************************************************
    @Override
    public UserDto getUserByEmail(String email) {

        // 1. Get email by user entity by custom method - findByEmail
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found as per email"));

        // 2. Convert user to Dto & return
        return entityToDto(user);
    }




    //Search User **********************************************************************************
    @Override
    public List<UserDto> searchUser(String keyword) {

        // 1. Get from user entity findByNameContaining(Keyword)
        List<User> users = userRepository.findByNameContaining(keyword);

        // 2. Convert List Users into List UserDto
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());

        // 3. Return users from dtoList
        return dtoList;
    }





    //Manually Mapping***********************************************************************
    //DTO to Entity method for User &
    // Entity to DTO method for user

    // Entity -> Dto (Convert method)
    private UserDto entityToDto(User savedUser) {

        //Manual method to  convert entity to Dto
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName())
//                .build();

        // 1. Library (modelMapper) to convert - entity -> Dto
            return mapper.map(savedUser, UserDto.class);
    }


    // DTO -> Entity (Convert method)
    private User dtoToEntity(UserDto userDto) {

        //Manual method to convert Dto to entity

//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .imageName(userDto.getImageName())
//                .build();

        // 1. Library (modelMapper) to covert - Dto to entity
        return mapper.map(userDto, User.class);
    }



}
