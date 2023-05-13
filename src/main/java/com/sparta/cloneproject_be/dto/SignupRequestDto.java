package com.sparta.cloneproject_be.dto;


import lombok.Data;

@Data
public class SignupRequestDto {

//    @Size
//    @Pattern(regexp = "")
//    @NotBlank
    private String email;

//    @Size로
//    @Pattern(regexp = "")
//    @NotBlank
    private String password;

//    @Size
//    @Pattern(regexp = "")
//    @NotBlank
    private String nickname;
}
