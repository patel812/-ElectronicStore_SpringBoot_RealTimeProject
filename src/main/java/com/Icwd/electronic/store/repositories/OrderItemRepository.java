package com.Icwd.electronic.store.repositories;

import com.Icwd.electronic.store.entities.Order;
import com.Icwd.electronic.store.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {




}
