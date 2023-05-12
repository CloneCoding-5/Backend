package com.sparta.cloneproject_be.dto;

import lombok.Getter;

@Getter
public class ExceptionDto {
    String message;

    public ExceptionDto(String message) {
        this.message = message;
    }
}
