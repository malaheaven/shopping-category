package com.musinsa.shoppingcategory.service.impl;

import com.musinsa.shoppingcategory.domain.Category;
import com.musinsa.shoppingcategory.dto.CategoryDto;
import com.musinsa.shoppingcategory.dto.RequestDto;
import com.musinsa.shoppingcategory.respository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private CategoryRepository categoryRepository;


    @AfterEach
    void deleteAll() {
        categoryRepository.deleteAll();
    }


    @Test
    @DisplayName("카테고리 등록: 맨 상위 카테고리 등록")
    @Transactional
    void addCategory1() {

        CategoryDto categoryDto = categoryService.addCategory(RequestDto.builder()
                .categoryName("상의")
                .build());

        Category category = categoryRepository.findById(categoryDto.getId()).get();

        assertThat(category.getName()).isEqualTo("상의");
        assertThat(category.getDepth()).isEqualTo(1L);
        assertThat(category.getParent()).isNull();
    }

    @Test
    @DisplayName("카테고리 등록: 하위 카테고리 등록")
    @Transactional
    void addCategory2() {

        CategoryDto parentDto = categoryService.addCategory(RequestDto.builder()
                .categoryName("상의")
                .build());

        CategoryDto categoryDto = categoryService.addCategory(RequestDto.builder()
                .parentCategoryId(parentDto.getId())
                .categoryName("상의의 하위 카테고리")
                .build());


        Category category = categoryRepository.findById(categoryDto.getId()).get();

        assertThat(category.getName()).isEqualTo("상의의 하위 카테고리");
        assertThat(category.getDepth()).isEqualTo(2L);
        assertThat(category.getParent()).isNotNull();
        assertThat(category.getParent().getName()).isEqualTo("상의");
    }

    @Test
    @DisplayName("카테고리 등록: DuplicateKeyException")
    void addDuplicateKeyException() {
        categoryService.addCategory(RequestDto.builder()
                .categoryName("상의")
                .build());


        assertThrows(DuplicateKeyException.class,
                () -> categoryService.addCategory(RequestDto.builder()
                        .categoryName("상의")
                        .build()));
    }

    @Test
    @DisplayName("카테고리 수정")
    void update() {

        CategoryDto categoryDto = categoryService.addCategory(RequestDto.builder()
                .categoryName("상의")
                .build());

        CategoryDto modifyCategory = categoryService.modifyCategory(String.valueOf(categoryDto.getId()), RequestDto.builder()
                .categoryName("하의")
                .build());

        Category category = categoryRepository.findById(modifyCategory.getId()).get();

        assertThat(categoryDto.getId()).isEqualTo(category.getId());
        assertThat(categoryDto.getDepth()).isEqualTo(category.getDepth());
        assertThat(category.getName()).isEqualTo("하의");
    }

    @Test
    @DisplayName("카테고리 삭제")
    void delete() {

        CategoryDto categoryDto = categoryService.addCategory(RequestDto.builder()
                .categoryName("상의")
                .build());

        assertTrue(categoryRepository.findById(categoryDto.getId()).isPresent());

        categoryService.deleteCategory(String.valueOf(categoryDto.getId()));

        assertThrows(NoSuchElementException.class,
                () -> categoryRepository.findById(categoryDto.getId()).get());

    }
}
