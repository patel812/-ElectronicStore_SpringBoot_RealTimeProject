package com.Icwd.electronic.store.repositories;

import com.Icwd.electronic.store.entities.Cart;
import com.Icwd.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByUser(User user);

}
