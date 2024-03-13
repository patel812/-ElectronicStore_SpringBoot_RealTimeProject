package com.Icwd.electronic.store.services;

import com.Icwd.electronic.store.dtos.OrderDto;
import com.Icwd.electronic.store.dtos.PageableResponse;

import java.util.List;

public interface OrderService
{

    //Create order
        OrderDto createOrder(OrderDto orderDto, String userId, String cartId);


    //Remove order
        void removeOrder(String orderId);


    //Get Order of user
        List<OrderDto> getOrdersOfUser(String userId);


    //Get orders
        PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);



    //order methods(Logic) related to order



}
