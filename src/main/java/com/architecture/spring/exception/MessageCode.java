package com.architecture.spring.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Message Code
 * @author JENNI
 * @version 1.0
 * @since 2022.05.24
 */

@Getter
public enum MessageCode {
    회원가입_성공("MEM_100", "회원가입에 성공하였습니다."),
    회원가입_실패("MEM_101", "회원가입에 실패하였습니다."),
    회원가입_중복("MEM_102", "중복된 %s입니다."),
    로그인_성공("MEM_110", "로그인에 성공하였습니다."),
    로그인_실패("MEM_111", "로그인에 실패하였습니다."),
    로그인_ID("MEM_102", "가입되지 않은 Id입니다."),
    로그인_PASSWORD("MEM_103", "잘못된 비밀번호입니다."),
    회원_정보_조회("MEM_104", "회원 정보 조회에 실패하였습니다."),
    회원_정보_EMPTY("MEM_105", "조회된 회원이 없습니다."),
    TOKEN_유효성("ERR_100", "token이 유효하지 않습니다."),
    테스트("TEST_999", "테스트");

    private String success;
    private String code;
    private String status_message;

    MessageCode(String code, String status_message) {
        this.code = code;
        this.status_message = status_message;
    }

    public String getStatus_message(String[] status_message) {
        return String.format(getStatus_message(), status_message);
    }
}