package com.Icwd.electronic.store.services;


import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.dtos.ProductDto;

import java.util.List;

public interface ProductService {

    //Create
    ProductDto create(ProductDto productDto);

    //Update
    ProductDto update(ProductDto productDto, String productId);

    //Delete
    void delete(String productId);

    //Get Single
    ProductDto getSingle(String productId);

    //Get All
    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //Get All : Live
    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

    //Search Product
    PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir);

    //Other Methods



}
