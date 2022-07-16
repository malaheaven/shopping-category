package com.musinsa.shoppingcategory.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@NoArgsConstructor
public class RequestDto {

    @Nullable
    private Long parentCategoryId;

    @NotEmpty
    private String categoryName;

}
