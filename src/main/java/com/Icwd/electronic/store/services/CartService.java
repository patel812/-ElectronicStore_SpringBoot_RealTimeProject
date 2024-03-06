package com.Icwd.electronic.store.services;

import com.Icwd.electronic.store.dtos.AddItemToCartRequest;
import com.Icwd.electronic.store.dtos.CartDto;

public interface CartService {

    //add items to cart
    //Case 1 : cart for user is not available : we will create the cart and the
    //Case 2 : cart available add the items to cart


    CartDto addItemToCart(String userId, AddItemToCartRequest request);


    //Remove item from cart
    void removeItemFromCart(String userId, int cartItem);

    //Remove all items from cart
    void clearCart(String userId);

}
