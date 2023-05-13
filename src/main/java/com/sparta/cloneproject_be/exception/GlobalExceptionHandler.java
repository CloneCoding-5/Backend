package com.sparta.cloneproject_be.exception;

import com.sparta.cloneproject_be.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<MessageDto> handleValidException(MethodArgumentNotValidException ex) {
        MessageDto messageDto = new MessageDto("유효성 검사를 통과하지 못했습니다.");
        return ResponseEntity.status(400).body(messageDto);
    }

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<MessageDto> handleCustomException(CustomException ex) {
        MessageDto messageDto = new MessageDto(ex.getErrorMessage().getMessage());
        int statusCode = ex.getErrorMessage().getStatusCode();
        return ResponseEntity.status(statusCode).body(messageDto);
    }
}
