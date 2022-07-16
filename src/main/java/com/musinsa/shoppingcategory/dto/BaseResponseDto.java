package com.musinsa.shoppingcategory.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.shoppingcategory.enums.BaseResponseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseDto<T> {

    private Integer code;
    private String message;

    @Nullable
    T result;

    public static <T> BaseResponseDto<T> success() {
        return new BaseResponseDto<>(BaseResponseType.SUCCESS.getCode(), BaseResponseType.SUCCESS.getMessage(), null);
    }

    public static <T> BaseResponseDto<T> success(@Nullable final T result) {
        return new BaseResponseDto<>(BaseResponseType.SUCCESS.getCode(), BaseResponseType.SUCCESS.getMessage(), result);
    }

    public static <T> BaseResponseDto<T> from(final BaseResponseType baseResponseType) {
        return new BaseResponseDto<>(baseResponseType.getCode(), baseResponseType.getMessage(), null);
    }

    public static <T> BaseResponseDto<T> from(final BaseResponseType baseResponseType, String message) {
        return new BaseResponseDto<>(baseResponseType.getCode(), message, null);
    }

}
