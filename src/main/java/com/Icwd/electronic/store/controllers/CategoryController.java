package com.Icwd.electronic.store.controllers;


import com.Icwd.electronic.store.dtos.ApiResponseMessage;
import com.Icwd.electronic.store.dtos.CategoryDto;
import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.dtos.ProductDto;
import com.Icwd.electronic.store.services.CategoryService;
import com.Icwd.electronic.store.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    //Create----------------------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto){

        //Call Service to save object
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        //Return
        return new  ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }



    //Update-----------------------------------------------------------------------------------
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> update(@PathVariable String categoryId, @RequestBody CategoryDto categoryDto){
        //Call Service to update object
        CategoryDto updateCategory = categoryService.update(categoryDto, categoryId);
        //Return
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);

    }



    //Delete-------------------------------------------------------------------------------------------
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> delete(@PathVariable String categoryId){

        //Call service to delete object
        categoryService.delete(categoryId);

        //Making response message through builder
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("Your Category is Deleted ")
                .status(HttpStatus.OK)
                .success(true)
                .build();

        //Return the response
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);

    }



    //Get All--------------------------------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll
    (
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false ) int pageSize,

            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    )
    {

        PageableResponse<CategoryDto> all = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }



    //Get Single---------------------------------------------------------------------------------------
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> get(@PathVariable String categoryId){
        //Get Single id from object
        CategoryDto categoryDto = categoryService.get(categoryId);

        //Return Dto & Response
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }


    //Create product with category Api--------------------------------------------------------------------
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createWithCategory
            (
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto productDto
            )
    {
        ProductDto withCategoryProduct = productService.createWithCategory(productDto, categoryId);
        return new ResponseEntity<>(withCategoryProduct, HttpStatus.CREATED);
    }


    //Update category of product
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct
    (
            @PathVariable String categoryId ,
            @PathVariable String productId
    )

    {
        ProductDto productDto = productService.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }





}
