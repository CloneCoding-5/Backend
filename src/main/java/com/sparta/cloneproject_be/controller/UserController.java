package com.sparta.cloneproject_be.controller;

import com.sparta.cloneproject_be.dto.LoginRequestDto;
import com.sparta.cloneproject_be.dto.MessageDto;
import com.sparta.cloneproject_be.dto.SignupRequestDto;
import com.sparta.cloneproject_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // Sign up
    @PostMapping("/signup")
    public ResponseEntity<MessageDto> signup(@RequestBody SignupRequestDto signupRequestDto){
        return userService.signup(signupRequestDto);
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<MessageDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return userService.login(loginRequestDto, response);
    }
}
