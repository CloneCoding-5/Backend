package com.sparta.cloneproject_be.exception;

import com.sparta.cloneproject_be.dto.ExceptionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionDto> handleValidException(MethodArgumentNotValidException ex) {
        ExceptionDto exceptionDto = new ExceptionDto("유효성 검사를 통과하지 못했습니다.");
        return ResponseEntity.status(400).body(exceptionDto);
    }

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<ExceptionDto> handleCustomException(CustomException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(exceptionDto);
    }
}
