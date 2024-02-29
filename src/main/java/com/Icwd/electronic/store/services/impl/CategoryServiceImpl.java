package com.Icwd.electronic.store.services.impl;

import com.Icwd.electronic.store.dtos.CategoryDto;
import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.entities.Category;
import com.Icwd.electronic.store.exceptions.ResourceNotFoundException;
import com.Icwd.electronic.store.helper.Helper;
import com.Icwd.electronic.store.repositories.CategoryRepository;
import com.Icwd.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    public ModelMapper mapper;


    //Save------------------------------------------------------------------
    @Override
    public CategoryDto create(CategoryDto categoryDto) {


        //Creating CategoryId : randomly

        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category = mapper.map(categoryDto, Category.class);



        Category saveCategory = categoryRepository.save(category);
        return mapper.map(saveCategory, CategoryDto.class);
    }



    //Update-------------------------------------------------------------------
    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {

        //Get category of given id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("CategoryId not found !!"));

        //Update one by one
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        //Save category
        Category updatedCategory = categoryRepository.save(category);

        //Return in Category Dto
        return mapper.map(updatedCategory, CategoryDto.class);
    }



    //Delete--------------------------------------------------------------------------------
    @Override
    public void delete(String categoryId) {

        //Get category of given id
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found !!"));

        //delete given id category
        categoryRepository.delete(category);

    }


    //Get All-----------------------------------------------------------------------------------
    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {

        //Sort in dec & Asse
        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        //Page
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        //Get All
        Page<Category> page = categoryRepository.findAll(pageable);

        //Convert into Dto
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);

        //Return
        return pageableResponse;
    }




    //Get Single ---------------------------------------------------------------------------------------
    @Override
    public CategoryDto get(String categoryId) {

        //Get by Id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found !!!"));

        //Return in Dto
        return mapper.map(category, CategoryDto.class);
    }
}
