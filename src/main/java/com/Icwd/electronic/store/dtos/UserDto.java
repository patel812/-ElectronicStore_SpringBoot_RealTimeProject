package com.Icwd.electronic.store.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto {

    //To take all properties of entity into UserDto (Dto is a middleware for data transfer
    // from controller to service( controller -> Dto -> service -> entity -> Mysql)

    private String userId;

    @Size(min = 3, max = 25, message = "Invalid Name!!")
    private String name;

    @Email(message = "Invalid user email!!")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required!!")
    private String password;

    @Size(min = 4, max = 6, message = "Invalid gender!!")
    private String gender;

    @NotBlank(message = "Write something about yourself !!")
    private String about;


    private String imageName;


    //@Pattern
    //Custom validator

}
