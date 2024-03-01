package com.Icwd.electronic.store.controllers;


import com.Icwd.electronic.store.dtos.ApiResponseMessage;
import com.Icwd.electronic.store.dtos.ImageResponse;
import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.dtos.ProductDto;
import com.Icwd.electronic.store.services.FileService;
import com.Icwd.electronic.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;


    //Create Api----------------------------------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto productDto){
        ProductDto productDto1 = productService.create(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }


    //Update Api----------------------------------------------------------------------------------------------
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> update(@RequestBody ProductDto productDto, @PathVariable String productId){
        ProductDto updateProductDTo = productService.update(productDto, productId);
        return new ResponseEntity<>(updateProductDTo, HttpStatus.OK);
    }



    //delete Api----------------------------------------------------------------------------------------------
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> delete(@PathVariable String productId){

        productService.delete(productId);

        ApiResponseMessage response = ApiResponseMessage.builder()
                .message("Product is deleted ")
                .status(HttpStatus.OK)
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //Get Single Api----------------------------------------------------------------------------------------------
     @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingle(@PathVariable String productId){
         ProductDto singleProduct = productService.getSingle(productId);
         return new ResponseEntity<>(singleProduct, HttpStatus.OK);
     }


    //Get All Api----------------------------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll
            (
             @RequestParam (value = "pageNumber", defaultValue = "0", required = false)int pageNumber,
             @RequestParam (value = "pageSize", defaultValue = "10", required = false) int pageSize,
             @RequestParam (value = "sortBy", defaultValue = "title" , required = false) String sortBy,
             @RequestParam (value = "sortDir", defaultValue = "asc", required = false) String sortDir
            )
    {
        PageableResponse<ProductDto> allProductPage = productService.getAll(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(allProductPage, HttpStatus.OK);
    }


    //Get all live----------------------------------------------------------------------------------------------
    @GetMapping("/allLive")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive
            (
                    @RequestParam (value = "pageNumber", defaultValue = "0" , required = false) int pageNumber,
                    @RequestParam (value = "pageSize", defaultValue = "10", required = false) int pageSize,
                    @RequestParam (value = "sortBy", defaultValue = "title", required = false) String sortBy,
                    @RequestParam (value = "sortDir", defaultValue = "asc", required = false) String sortDir
            )

    {
        PageableResponse<ProductDto> allLiveProduct = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allLiveProduct, HttpStatus.OK);

    }


    //Search all ----------------------------------------------------------------------------------------------
    @GetMapping("/search/{subTitle}")
    public ResponseEntity<PageableResponse<ProductDto>> searchByTitle
            (
                @PathVariable String subTitle,

                @RequestParam (value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                @RequestParam (value = "pageSize", defaultValue = "10", required = false) int pageSize,

                @RequestParam (value = "sortBy", defaultValue = "title", required = false) String sortBy,
                @RequestParam (value = "sortDir", defaultValue = "asc", required = false) String sortDir
            )
    {

        PageableResponse<ProductDto> searchResponse = productService.searchByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
    }



    //Upload Image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable String productId,
            @RequestParam ("productImage") MultipartFile image
            ) throws IOException {

        String fileName = fileService.uploadFile(image, imagePath);
        //Get User
        ProductDto productDto = productService.getSingle(productId);

        productDto.setProductImageName(fileName);
        ProductDto updateProduct = productService.update(productDto, productId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(updateProduct.getProductImageName())
                .success(true)
                .status(HttpStatus.OK)
                .message("Your Image will upload")
                .build();

        return new ResponseEntity<>(imageResponse, HttpStatus.OK);

    }



    //Serve image





}
