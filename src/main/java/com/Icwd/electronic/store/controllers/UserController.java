package com.Icwd.electronic.store.controllers;

import com.Icwd.electronic.store.dtos.ApiResponseMessage;
import com.Icwd.electronic.store.dtos.ImageResponse;
import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.dtos.UserDto;
import com.Icwd.electronic.store.entities.User;
import com.Icwd.electronic.store.services.FileService;
import com.Icwd.electronic.store.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//Controller take request
@RestController

//handle Url Request
@RequestMapping("/users")
public class UserController {


    //To get data from UserService into UserDto
    @Autowired
    private UserService userService;


    @Autowired
    private FileService fileService;


    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    //Create API------------------------------------------------------------------------------------
    @PostMapping
    //     Status code | Return type        | To send data | To get Data
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){

       UserDto userDto1 = userService.createUser(userDto);
       //                             data  | Status code
       return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }



    //Update API-------------------------------------------------------------------------------------

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @Valid @RequestBody UserDto userDto){

       UserDto userDto2 = userService.updateUser(userDto, userId);

       return new ResponseEntity<>(userDto2, HttpStatus.OK);

    }


    //Delete Api---------------------------------------------------------------------------------------
    @DeleteMapping("/{userId}")
    //                 Return Type class message
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId){

            // Don't hold data for delete
            userService.deleteUser(userId);

        ApiResponseMessage message  =  ApiResponseMessage
                .builder() //Using builder pattern for message opening
                .message("User is deleted Successfully!!") //message use of class ApiResponseMessage
                .success(true) // success use as a boolean of class ApiResponseMessage
                .status(HttpStatus.OK) //status use for Http resp of class ApiResponseMessage
                .build(); // builder pattern closing

                //Only pass return type in response as a message ""
                return new ResponseEntity<>(message, HttpStatus.OK);
    }



    //Get All Api------------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUser
    (
            //For Pagination
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,

            // 1. Sorting
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    )
    {
        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize, sortBy, sortDir), HttpStatus.OK);
    }


    //Get Single Api---------------------------------------------------------------------------------
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId){

        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK );

    }


    //Get by email Api---------------------------------------------------------------------------------
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){

        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }


    //Search user Api-----------------------------------------------------------------------------------

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){

        return new ResponseEntity<>(userService.searchUser(keyword), HttpStatus.OK);
    }



    //Upload User image

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @PathVariable String userId, @RequestParam("userImage")MultipartFile image
            ) throws IOException {

        String imageName = fileService.uploadFile(image, imageUploadPath);

        UserDto user = userService.getUserById(userId);

        user.setImageName(imageName);

        UserDto userDto = userService.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse
                .builder()
                .imageName(imageName)
                .success(true)
                .status(HttpStatus.CREATED)
                .build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }


    //Serve user image







}
