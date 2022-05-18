package com.architecture.spring.service;

import com.architecture.spring.model.response.ApiResponseModel;
import org.springframework.stereotype.Service;

/**
 * api response 관련 처리를 하는 service
 * @author JENNI
 * @version 1.0
 * @since 2022.05.09
 */

@Service
public class ApiResponseService {
    public ApiResponseModel getApiResponse(String success, String status_msg, Object result) {
        return ApiResponseModel.builder()
                .success(success)
                .status_message(status_msg)
                .result(result)
                .build();
    }
}
