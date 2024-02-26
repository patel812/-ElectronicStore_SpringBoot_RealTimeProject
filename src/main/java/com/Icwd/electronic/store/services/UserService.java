package com.Icwd.electronic.store.services;


import com.Icwd.electronic.store.dtos.UserDto;

import java.util.List;

public interface UserService {

    //Create
 //ReturnType -> MethodName  ->Object  -> ObjectType
     UserDto     createUser   (UserDto    userDto);


    //Update
    UserDto  updateUser(UserDto userDto, String userId);


    //delete (Not need a return type)
    void deleteUser(String userId);


    //get all users
    List<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);


    //get single user by id
    UserDto getUserById(String userId);


    //get single user by email id
    UserDto getUserByEmail(String email);


    //Search user
    List<UserDto> searchUser(String keyword);


    //Other user specific  features


}
