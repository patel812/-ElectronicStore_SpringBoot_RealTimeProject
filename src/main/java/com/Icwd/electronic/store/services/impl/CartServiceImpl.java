package com.Icwd.electronic.store.services.impl;

import com.Icwd.electronic.store.dtos.AddItemToCartRequest;
import com.Icwd.electronic.store.dtos.CartDto;
import com.Icwd.electronic.store.entities.Cart;
import com.Icwd.electronic.store.entities.CartItem;
import com.Icwd.electronic.store.entities.Product;
import com.Icwd.electronic.store.entities.User;
import com.Icwd.electronic.store.exceptions.ResourceNotFoundException;
import com.Icwd.electronic.store.repositories.CartRepository;
import com.Icwd.electronic.store.repositories.ProductRepository;
import com.Icwd.electronic.store.repositories.UserRepository;
import com.Icwd.electronic.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;




    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId = request.getProductId();

        //Fetch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found in dataBase"));
        //Fetch the User from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found in dataBase !!"));

        Cart cart = null;

        try
            {
            cart = cartRepository.findByUser(user).get();
            }
        catch (NoSuchElementException e)
            {
                cart = new Cart();
                cart.setCartId(UUID.randomUUID().toString());
                cart.setCreatedAt(new Date());
            }

        //perform cart operations
        //if cart items already present; then update

        AtomicReference<Boolean> updated = new AtomicReference<>(false);

        List<CartItem> items = cart.getItems();
         List<CartItem> updatedItems = items.stream().map(item->{

            if(item.getProduct().getProductId().equals(productId)){
                //items already present in cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity*product.getPrice());
                updated.set(true);


            }

            return item;

        }).collect(Collectors.toList());


        //Create items
        if(!updated.get()){
         CartItem cartItem = CartItem.builder()
                .quantity(quantity)
                .totalPrice(quantity * product.getPrice())
                .cart(cart)
                .product(product)
                .build();

        cart.getItems().add(cartItem);
        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart, CartDto.class);

    }




    @Override
    public void removeItemFromCart(String userId, int cartItem) {

    }

    @Override
    public void clearCart(String userId) {

    }



}
