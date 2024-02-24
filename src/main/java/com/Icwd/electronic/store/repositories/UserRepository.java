package com.Icwd.electronic.store.repositories;

import com.Icwd.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//                                 i.Use Jpa Method, <Entity class name & id type>
public interface UserRepository extends JpaRepository<User, String>
{
    //We are extending to get all Jpa database methods by inheritance
    //All method we get dynamic


    //Custom method
    Optional<User>  findByEmail(String email);

    Optional<User>  findByEmailAndPassword(String email, String password);

    List<User> findByNameContaining(String keywords);

}
