package com.Icwd.electronic.store.services;

import com.Icwd.electronic.store.dtos.CategoryDto;
import com.Icwd.electronic.store.dtos.PageableResponse;

public interface CategoryService {

    //Create
    CategoryDto create (CategoryDto categoryDto);

    //Update
    CategoryDto update (CategoryDto categoryDto, String categoryId);

    //delete
    void delete (String categoryId);

    //Get all
    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //Get single category detail
    CategoryDto get(String categoryId);

    //Search:


}
