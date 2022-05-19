package com.architecture.spring.exception;

import lombok.Getter;

/**
 * Service 관련 Exception 처리
 * @author JENNI
 * @version 1.0
 * @since 2022.05.19
 */

@Getter
public class ServiceException extends Exception {
    private ErrorCode errorCode;

    public ServiceException(){
        new Exception();
    }

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getStatus_message());
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCode, String status_message) {
        super(errorCode.getStatus_message(status_message));
        this.errorCode = errorCode;
    }
}
