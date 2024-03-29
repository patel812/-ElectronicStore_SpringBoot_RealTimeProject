package com.Icwd.electronic.store.services.impl;

import com.Icwd.electronic.store.dtos.AddItemToCartRequest;
import com.Icwd.electronic.store.dtos.CartDto;
import com.Icwd.electronic.store.entities.Cart;
import com.Icwd.electronic.store.entities.CartItem;
import com.Icwd.electronic.store.entities.Product;
import com.Icwd.electronic.store.entities.User;
import com.Icwd.electronic.store.exceptions.BadApiRequest;
import com.Icwd.electronic.store.exceptions.ResourceNotFoundException;
import com.Icwd.electronic.store.repositories.CartItemRepository;
import com.Icwd.electronic.store.repositories.CartRepository;
import com.Icwd.electronic.store.repositories.ProductRepository;
import com.Icwd.electronic.store.repositories.UserRepository;
import com.Icwd.electronic.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;


    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartItemRepository cartItemRepository;



    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId = request.getProductId();


        if(quantity<=0){
            throw new BadApiRequest("Requested quantity is not valid !!");
        }




        //Fetch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found in dataBase"));
        //Fetch the User from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found in dataBase !!"));

        Cart cart = null;

        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }

        //perform cart operations
        //if cart items already present; then update

        AtomicReference<Boolean> updated = new AtomicReference<>(false);

        List<CartItem> items = cart.getItems();
        List<CartItem> updatedItems = items.stream().map(item -> {

            if (item.getProduct().getProductId().equals(productId)) {
                //items already present in cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getPrice());
                updated.set(true);


            }

            return item;

        }).collect(Collectors.toList());


        //Create items
        Cart updatedCart = null;
        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();

            cart.getItems().add(cartItem);
            cart.setUser(user);
            updatedCart = cartRepository.save(cart);
        }
        return mapper.map(updatedCart, CartDto.class);

    }




        @Override
        public void removeItemFromCart(String userId, int cartItem)
        {

          CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(()-> new ResourceNotFoundException("Your cart item Id not found"));
            cartItemRepository.delete(cartItem1);

        }

        @Override
        public void clearCart(String userId) {

            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Id not found"));
            Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart user Id not found "));
            cart.getItems().clear();
            cartRepository.save(cart);
        }

    @Override
    public CartDto getCartByUser(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Your User Id not find"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Your Cart user Id not found"));
        return mapper.map(cart, CartDto.class);
    }


}
