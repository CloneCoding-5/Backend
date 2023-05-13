package com.sparta.cloneproject_be.service;

import com.sparta.cloneproject_be.dto.LoginRequestDto;
import com.sparta.cloneproject_be.dto.MessageDto;
import com.sparta.cloneproject_be.dto.SignupRequestDto;
import com.sparta.cloneproject_be.entity.User;
import com.sparta.cloneproject_be.exception.CustomException;
import com.sparta.cloneproject_be.exception.ErrorMessage;
import com.sparta.cloneproject_be.jwt.JwtUtil;
import com.sparta.cloneproject_be.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Builder
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public ResponseEntity<MessageDto> signup(SignupRequestDto signupRequestDto){
        String email = signupRequestDto.getEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String nickname = signupRequestDto.getNickname();

        // 이메일 중복 검사
        Optional<User> foundByEmail = userRepository.findByEmail(email);
        if (foundByEmail.isPresent()){
            throw new CustomException(ErrorMessage.ENROLLED_EMAIL);
        }

        // 닉네임 중복 검사
        Optional<User> foundByNickname = userRepository.findByNickname(nickname);
        if (foundByNickname.isPresent()){
            throw new CustomException(ErrorMessage.ENROLLED_NICKNAME);
        }

        // 유저 등록
        User user = User.builder()
                        .email(email)
                        .password(password)
                        .nickname(nickname).build();

        userRepository.save(user);

        return ResponseEntity.ok().body(new MessageDto("회원가입에 성공했습니다."));
    }

    // 로그인
    public ResponseEntity<MessageDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        // 이메일 검사
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorMessage.UNENROLLED_EMAIL));

        // 패스워드 검사
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new CustomException(ErrorMessage.PASSWORD_MISMATCH);
        }

        // 토큰 발급
        String token = jwtUtil.createToken(user);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        return ResponseEntity.ok().body(new MessageDto("로그인 성공"));
    }
}
