package com.Icwd.electronic.store.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

//To Create table in data Base
@Entity

@Table(name = "Users")
public class User {

    //Primary key or id
    @Id
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email" , unique = true)
    private String email;

    @Column(name = "user_password", length = 10)
    private String password;

    private String gender;

    @Column(length = 1000)
    private String about;

    @Column(name = "user_image_name")
    private String imageName;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Order> orders = new ArrayList<>();


}
