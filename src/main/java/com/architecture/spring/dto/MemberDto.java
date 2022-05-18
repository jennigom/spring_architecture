package com.architecture.spring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * 회원 정보를 담을 때 사용하는 dto
 * @author JENNI
 * @version 1.0
 * @since 2022.05.09
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class MemberDto {
    // 회원 Id
    private String userId;
    
    // 비밀번호
    private String password;
    
    // 회원 이름
    private String name;

    // 주민등록번호
    private String regNo;
}
