package com.architecture.spring.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * api return 값을 담아 전달하는 model
 * @author JENNI
 * @version 1.0
 * @since 2022.05.09
 */

@Builder
@Data
public class ApiResponseModel {
    // 성공 여부: success/fail
    private String success;

    // 응답 메세지
    private String status_message;

    // 결과 데이터
    private Object result;
}
