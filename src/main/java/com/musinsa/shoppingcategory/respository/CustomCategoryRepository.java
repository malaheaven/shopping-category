package com.musinsa.shoppingcategory.respository;

import com.musinsa.shoppingcategory.domain.Category;

import java.util.List;

public interface CustomCategoryRepository {

    List<Category> findCategory(String categoryId);
}
