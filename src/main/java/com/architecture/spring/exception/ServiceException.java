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
    private MessageCode messageCode;

    public ServiceException(){
        new Exception();
    }

    public ServiceException(MessageCode messageCode) {
        super(messageCode.getStatus_message());
        this.messageCode = messageCode;
    }

    public ServiceException(MessageCode messageCode, String[] status_message) {
        super(messageCode.getStatus_message(status_message));
        this.messageCode = messageCode;
    }
}
