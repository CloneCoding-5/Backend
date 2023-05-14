package com.sparta.cloneproject_be.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignupRequestDto {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 양식에 맞지 않습니다.")
    private String email;

    @Size(min = 4, max = 15, message = "비밀번호는 8 이상, 15 이하만 가능합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[a-zA-Z0-9`~!@#$%^&*()_=+|{};:,.<>/?]*$", message = "비밀번호는 영문과 숫자로 구성되어야하며 특수문자를 포함할 수 있습니다.")
    @NotBlank
    private String password;

    @Size(min = 2, max = 10,message = "[닉네임은 2글자~10자 사이로 입력해주세요]")
    @Pattern(regexp = "^[a-zA-Z가-힣]*$",message = "[닉네임은 알파벳 또는 한글의 형태로 입력해주세요]")
    @NotBlank
    private String nickname;
}
