package com.sparta.cloneproject_be.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    // 400 BAD REQUEST
    ENROLLED_EMAIL("중복된 이메일입니다."),
    ENROLLED_NICKNAME("중복된 닉네임입니다."),
    PASSWORD_MISMATCH("비밀번호를 확인해주세요."),
    OUT_OF_EMAIL_PATTERN("이메일 양식에 맞지 않습니다."),
    OUT_OF_PASSWORD_PATTERN("비밀번호 양식에 맞지 않습니다."), // 추후 프론트에서 비밀번호 유효성 검사 패턴을 정해주면 메시지 수정
    ALREADY_RESERVED("이미 예약되었습니다."),
    NOT_IMAGE("jpg, jpeg, png 파일만 업로드 가능합니다."),
    BLANK_EXIST("입력되지 않은 값이 있습니다. 모든 항목을 작성해주세요."),

    // 401 Unauthorized
    INVALID_TOKEN("유효하지 않은 토큰입니다."),

    // 403 FORBIDDEN
    CANNOT_DELETE_WISHLIST("직접 찜한 위시리트만 삭제 가능합니다."),
    CANNOT_CANCLE_RESERVATION("직접 등록한 예약만 취소 가능합니다."),
    CANNOT_UPDATE_POST("직접 작성한 게시글만 수정 가능합니다."),
    CANNOT_DELETE_POST("직접 작성한 게시글만 삭제 가능합니다."),

    // 404 NOT FOUND
    UNENROLLED_EMAIL("등록되지 않은 이메일입니다."),
    NON_EXIST_POST("존재하지 않는 게시글입니다.");







    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
