package com.sparta.cloneproject_be.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    int statusCode;
    String message;

    public CustomException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    // 사용 예시
    // throw new CustomException(HttpStatus.BAD_REQUEST.value(), ErrorMessage.ENROLLED_EMAIL.getMessage());
}