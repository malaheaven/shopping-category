package com.musinsa.shoppingcategory.service;

import com.musinsa.shoppingcategory.dto.RequestDto;
import com.musinsa.shoppingcategory.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> search(String categoryId);

    CategoryDto addCategory(RequestDto requestDto);

    CategoryDto modifyCategory(String categoryId, RequestDto requestDto);

    void deleteCategory(String categoryId);

}
