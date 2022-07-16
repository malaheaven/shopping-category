package com.musinsa.shoppingcategory.exception;

import com.musinsa.shoppingcategory.enums.BaseResponseType;

public class DataNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 7316354293156219457L;

    public DataNotFoundException() {
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public static DataNotFoundException from(String message) {
        return new DataNotFoundException(message);
    }

    public static DataNotFoundException from(final BaseResponseType returnCode) {
        return new DataNotFoundException(returnCode.getMessage());
    }
}
