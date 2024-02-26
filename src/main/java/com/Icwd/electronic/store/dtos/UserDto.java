package com.Icwd.electronic.store.dtos;

import com.Icwd.electronic.store.validate.ImageNameValid;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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


    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid user email!!!")
    @Email(message = "Invalid user email!!")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required!!")
    private String password;

    @Size(min = 4, max = 6, message = "Invalid gender!!")
    private String gender;

    @NotBlank(message = "Write something about yourself !!")
    private String about;

    @ImageNameValid
    private String imageName;


    //@Pattern
    //Custom validator

}
