package com.musinsa.shoppingcategory.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotEmpty;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestDto {

    @Nullable
    private Long parentCategoryId;

    @NotEmpty
    private String categoryName;

}
