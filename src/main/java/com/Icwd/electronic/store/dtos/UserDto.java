package com.Icwd.electronic.store.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
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
    private String name;
    private String email;
    private String password;
    private String gender;
    private String about;
    private String imageName;


}
