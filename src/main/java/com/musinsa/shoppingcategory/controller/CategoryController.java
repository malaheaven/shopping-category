package com.musinsa.shoppingcategory.controller;

import com.musinsa.shoppingcategory.dto.BaseResponseDto;
import com.musinsa.shoppingcategory.dto.RequestDto;
import com.musinsa.shoppingcategory.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category")
    public BaseResponseDto<?> search(@RequestParam(name = "categoryId", required = false, defaultValue = "ALL") String categoryId) {
        return BaseResponseDto.success(categoryService.search(categoryId));
    }

    @PostMapping(value = "/category")
    public BaseResponseDto<?> add(@Valid @RequestBody RequestDto requestDto) {
        return BaseResponseDto.success(categoryService.addCategory(requestDto));
    }

    @PutMapping("/category/{categoryId}")
    public BaseResponseDto<?> modify(@PathVariable("categoryId") String categoryId,
                                     @Valid @RequestBody RequestDto requestDto) {
        return BaseResponseDto.success(categoryService.modifyCategory(categoryId, requestDto));
    }

    @DeleteMapping("/category/{categoryId}")
    public BaseResponseDto<?> delete(@PathVariable("categoryId") String categoryId) {
        categoryService.deleteCategory(categoryId);
        return BaseResponseDto.success();
    }
}
