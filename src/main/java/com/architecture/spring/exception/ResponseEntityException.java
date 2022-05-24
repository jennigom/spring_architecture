package com.architecture.spring.exception;

import com.architecture.spring.model.response.ApiResponseModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 특정 Exception(@Valid, ...)이 발생했을 때 ApiResponseModel로 return 해주기 위해 ResponseEntityExceptionHandler를 상속받아 수정한 handler
 * @author JENNI
 * @version 1.1
 * @since 2022.05.19
 */

@ControllerAdvice   // 모든 controller 영역에서 발생할 수 있는 예외를 잡아줌
public class ResponseEntityException extends ResponseEntityExceptionHandler {
    @Override // @Valid error
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return new ResponseEntity<>(ApiResponseModel.builder()
                .success("fail")
                .status_message(ex.getBindingResult().getFieldError().getDefaultMessage())
                .result("").build(), HttpStatus.OK);
    }
}
