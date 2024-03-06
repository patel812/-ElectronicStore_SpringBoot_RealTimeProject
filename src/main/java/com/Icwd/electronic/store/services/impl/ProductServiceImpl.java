package com.Icwd.electronic.store.services.impl;

import com.Icwd.electronic.store.dtos.CategoryDto;
import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.dtos.ProductDto;
import com.Icwd.electronic.store.entities.Category;
import com.Icwd.electronic.store.entities.Product;
import com.Icwd.electronic.store.exceptions.ResourceNotFoundException;
import com.Icwd.electronic.store.helper.Helper;
import com.Icwd.electronic.store.repositories.CategoryRepository;
import com.Icwd.electronic.store.repositories.ProductRepository;
import com.Icwd.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;



    //Create implementation----------------------------------------------------------------------------------
    @Override
    public ProductDto create(ProductDto productDto) {

        //Create product id randomly
        String productIds = UUID.randomUUID().toString();
        productDto.setProductId(productIds);

        //Date
        productDto.setAddedDate(new Date());
        Product product = mapper.map(productDto, Product.class);
        Product saveProduct = productRepository.save(product);
        return mapper.map(saveProduct, ProductDto.class);
    }


    //Update implementation----------------------------------------------------------------------------------
    @Override
    public ProductDto update(ProductDto productDto, String productId) {

        //Find product by id
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Id Not Found!!"));

        //Set & Get Updated Product one by one
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());

        //Save update product
        Product updatedProduct = productRepository.save(product);

        //Return updated product into ProductDto
        return mapper.map(updatedProduct, ProductDto.class);
    }



    //delete implementation----------------------------------------------------------------------------------
    @Override
    public void delete(String productId) {
        //Get Product by id
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Id not found!!"));
        //Delete product
        productRepository.delete(product);
    }


    //getSingle implementation----------------------------------------------------------------------------------
    @Override
    public ProductDto getSingle(String productId) {
        //Get single product by id
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found!!"));
        //return the product into product Dto
        return mapper.map(product, ProductDto.class);
    }


    //getAll implementation----------------------------------------------------------------------------------
    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()) ;
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findAll(pageable);

        return Helper.getPageableResponse(page, ProductDto.class);
    }



    //Get_All_Live implementation----------------------------------------------------------------------------------
    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);

        return Helper.getPageableResponse(page, ProductDto.class);
    }



    //searchByTitle implementation----------------------------------------------------------------------------------
    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable);

        return Helper.getPageableResponse(page, ProductDto.class);
    }



    //Create product with category Api
    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {


        //Fetch the category from db
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found !!"));
        Product product = mapper.map(productDto, Product.class);

        //Create product id randomly
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);

        //Date
        productDto.setAddedDate(new Date());
        product.setCategory(category);

        Product saveProduct = productRepository.save(product);
        return mapper.map(saveProduct, ProductDto.class);

    }



    //update
    @Override
    public ProductDto updateCategory(String productId, String categoryId) {

        //Product fetch
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Id not found!!"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Id not found !!"));

        product.setCategory(category);
        Product saveProduct = productRepository.save(product);

        return mapper.map(saveProduct, ProductDto.class);
    }
}
