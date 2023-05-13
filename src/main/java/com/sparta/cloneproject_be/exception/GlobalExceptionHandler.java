package com.sparta.cloneproject_be.exception;

import com.sparta.cloneproject_be.dto.MessageDto;
import com.sparta.cloneproject_be.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<MessageDto> handleCustomException(CustomException ex) {
        MessageDto messageDto = new MessageDto(ex.getErrorMessage().getMessage());
        int statusCode = ex.getErrorMessage().getStatusCode();
        return ResponseEntity.status(statusCode).body(messageDto);
    }

    // Valid 예외 핸들러 (아이디 패스워드 유효성 검사)
    @ExceptionHandler({BindException.class})
    public ResponseEntity handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder sb = new StringBuilder();
        for ( FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage());
            sb.append(" ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
    }
}
