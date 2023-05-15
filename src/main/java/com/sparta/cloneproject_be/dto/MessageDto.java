package com.sparta.cloneproject_be.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MessageDto {
    String message;

    public MessageDto(String message) {
        this.message = message;
    }
}
