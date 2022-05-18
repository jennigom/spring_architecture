package com.architecture.spring.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 회원가입 시 파라미터를 받는 dto
 * @author JENNI
 * @version 1.0
 * @since 2022.05.09
 */

@Builder
@Data
public class MemberSignUpDto {
    /// 회원 Id
    @NotBlank(message = "회원 ID를 입력하세요.")
    @Pattern(regexp = "[a-zA-Z0-9]{5,10}",
            message = "아이디는 영문, 숫자만 가능하며 5~10 글자까지 가능합니다.")
    private String userId;

    // 비밀번호
    @NotBlank(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 영문과 숫자, 특수문자 조합으로 8~16 글자까지 가능합니다.")
    private String password;
    
    // 회원 이름
    @NotBlank(message = "이름을 입력하세요.")
    @Pattern(regexp = ".{1,50}",
            message = "이름은 1~50 글자까지 가능합니다.")
    private String name;

    // 주민등록번호
    @NotBlank(message = "주민등록번호를 입력하세요.")
    @Pattern(regexp = "\\d{6}\\-[1-4]\\d{6}",
            message = "정확한 주민등록번호를 입력하세요.")
    private String regNo;
}
