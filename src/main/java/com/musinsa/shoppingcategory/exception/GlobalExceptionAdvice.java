package com.musinsa.shoppingcategory.exception;

import com.musinsa.shoppingcategory.dto.BaseResponseDto;
import com.musinsa.shoppingcategory.enums.BaseResponseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponseDto<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("e : {}", e.getMessage());
        String message = e.getBindingResult().getFieldErrors().get(0).getField() + " " +
                e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return BaseResponseDto.from(BaseResponseType.BAD_REQUEST, message);
    }

    @ResponseBody
    @ExceptionHandler(DataNotFoundException.class)
    public BaseResponseDto<?> dataNotFoundException(DataNotFoundException e) {
        log.info("e: {}", e.getMessage());
        return BaseResponseDto.from(BaseResponseType.DATA_NOT_FOUND_EXCEPTION, e.getMessage());

    }

    @ResponseBody
    @ExceptionHandler(DuplicateKeyException.class)
    public BaseResponseDto<?> duplicateKeyException(DuplicateKeyException e) {
        log.info("e: {}", e.getMessage());
        return BaseResponseDto.from(BaseResponseType.DUPLICATE_KEY_EXCEPTION);
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponseDto<?> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info("e : {}", e.getMessage());
        return BaseResponseDto.from(BaseResponseType.METHOD_NOT_ALLOWED);
    }

    @ResponseBody
    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResponseDto<?> noHandlerFoundException(NoHandlerFoundException e) {
        log.info("e : {}", e.getMessage());
        return BaseResponseDto.from(BaseResponseType.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public BaseResponseDto<?> errorHandler(Exception e) {
        log.info("e : {}", e.getMessage());
        return BaseResponseDto.from(BaseResponseType.INTERNAL_SERVER_ERROR);
    }
}
