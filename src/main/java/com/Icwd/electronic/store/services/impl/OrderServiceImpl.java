package com.Icwd.electronic.store.services.impl;

import com.Icwd.electronic.store.dtos.OrderDto;
import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.entities.*;
import com.Icwd.electronic.store.exceptions.BadApiRequest;
import com.Icwd.electronic.store.exceptions.ResourceNotFoundException;
import com.Icwd.electronic.store.repositories.CartRepository;
import com.Icwd.electronic.store.repositories.OrderRepository;
import com.Icwd.electronic.store.repositories.UserRepository;
import com.Icwd.electronic.store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;


    //Create Order---------------------------------------------------------------
    @Override
    public OrderDto createOrder(OrderDto orderDto, String userId, String cartId) {

        //fetch User
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Id not found by given Id"));

        //fetch cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart Id not found "));


        List<CartItem> cartItems = cart.getItems();

        if (cartItems.size() <= 0){

            throw new BadApiRequest("Invalid number of items in cart!!");
        }

        //Other check

        Order.builder()
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderedDate(new Date())
                .deliveredDate(orderDto.getDeliveredDate())
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        //orderItems, amount
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {

            //CartItem -> OrderItem

            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .order(new Order())
                    .build();

            return orderItem;
        }).collect(Collectors.toList());



        return null;

    }



    @Override
    public void removeOrder(String orderId) {

    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        return null;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        return null;
    }
}
