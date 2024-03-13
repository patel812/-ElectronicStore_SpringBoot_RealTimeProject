package com.Icwd.electronic.store.repositories;

import com.Icwd.electronic.store.entities.Cart;
import com.Icwd.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByUser(User user);

}
