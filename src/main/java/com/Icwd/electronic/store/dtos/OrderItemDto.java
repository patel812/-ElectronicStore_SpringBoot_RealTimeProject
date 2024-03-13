package com.Icwd.electronic.store.dtos;

import com.Icwd.electronic.store.entities.Order;
import com.Icwd.electronic.store.entities.Product;
import jakarta.persistence.*;

public class OrderItemDto {

    private int orderItemId;
    private int quantity;
    private int totalPrice;
    private ProductDto product;
    private Order order;

}
