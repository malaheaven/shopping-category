package com.musinsa.shoppingcategory.service.impl;

import com.musinsa.shoppingcategory.domain.Category;
import com.musinsa.shoppingcategory.dto.CategoryDto;
import com.musinsa.shoppingcategory.dto.RequestDto;
import com.musinsa.shoppingcategory.enums.BaseResponseType;
import com.musinsa.shoppingcategory.exception.DataNotFoundException;
import com.musinsa.shoppingcategory.respository.CategoryRepository;
import com.musinsa.shoppingcategory.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> DataNotFoundException.from("Category Not Found"));
    }

    @Override
    @Cacheable(cacheNames = "category", key = "#categoryId")
    public List<CategoryDto> search(String categoryId) {
        return categoryRepository.findCategory(categoryId).stream().map(CategoryDto::toDto).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(cacheNames = "category", allEntries = true)
    @Transactional
    public CategoryDto addCategory(RequestDto requestDto) {
        Category.CategoryBuilder category = Category.builder();

        if (requestDto.getParentCategoryId() != null) {
            Category parent = getCategory(requestDto.getParentCategoryId());
            category.parent(parent);
            category.depth(parent.getDepth() + 1);
        }

        Category save;

        try {
            save = categoryRepository.save(category.name(requestDto.getCategoryName()).build());
        } catch (DataIntegrityViolationException e) {
            log.info("e : {}", e.getMessage());
            throw new DuplicateKeyException(BaseResponseType.DUPLICATE_KEY_EXCEPTION.getMessage());
        }
        return CategoryDto.toDto(save);
    }


    @Override
    @CacheEvict(cacheNames = "category", allEntries = true)
    @Transactional
    public CategoryDto modifyCategory(String categoryId, RequestDto requestDto) {
        Category category = getCategory(Long.parseLong(categoryId));
        category.updateCategory(requestDto.getCategoryName());

        Category save;

        try {
            save = categoryRepository.save(category);

        } catch (DataIntegrityViolationException e) {
            log.info("e : {}", e.getMessage());
            throw new DuplicateKeyException(BaseResponseType.DUPLICATE_KEY_EXCEPTION.getMessage());
        }

        return CategoryDto.toDto(save);
    }

    @Override
    @CacheEvict(cacheNames = "category", allEntries = true)
    @Transactional
    public void deleteCategory(String categoryId) {
        categoryRepository.delete(getCategory(Long.parseLong(categoryId)));
    }
}
