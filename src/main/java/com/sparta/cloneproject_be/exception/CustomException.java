package com.sparta.cloneproject_be.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    ErrorMessage errorMessage;

    public CustomException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    // 사용 예시
    // throw new CustomException(ErrorMessage.~~);
}