package com.sparta.cloneproject_be.dto;

import lombok.Getter;

@Getter
public class MessageDto {
    String message;

    public MessageDto(String message) {
        this.message = message;
    }
}
