package com.example.demo.auth.enums;

public enum ResponseMessage {
    REGISTER_SUCCESS("회원가입이 성공적으로 완료되었습니다."),
    REGISTER_FAILED("회원가입을 실패했습니다.");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }



}
