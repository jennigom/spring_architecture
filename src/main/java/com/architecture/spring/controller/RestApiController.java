package com.architecture.spring.controller;

import com.architecture.spring.dao.MemberDao;
import com.architecture.spring.dto.MemberDto;
import com.architecture.spring.dto.MemberSignUpDto;
import com.architecture.spring.dto.MemberLoginDto;
import com.architecture.spring.exception.ServiceException;
import com.architecture.spring.model.response.ApiResponseModel;
import com.architecture.spring.model.response.TokenInfoModel;
import com.architecture.spring.security.JwtTokenProvider;
import com.architecture.spring.service.MemberService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * RestAPI Controller
 * @author JENNI
 * @version 1.1
 * @since 2022.05.17
 */

@RequiredArgsConstructor    // 생성자 주입
@RestController             // RESTful 웹 서비스에서 사용하는 컨트롤러(@Controller + @ResponseBody)
public class RestApiController extends BaseController {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final MemberDao memberDao;

    @ApiOperation(value="회원가입", notes="")
    @PostMapping("/v1/signup")
    public ApiResponseModel signup(@RequestBody @ApiParam(value="회원가입할 때 필요한 회원 정보", required = true) @Valid MemberSignUpDto member) {
        try {
            MemberDto signupResult = memberService.signup(member);
            return getApiResponse("success", "회원가입에 성공하였습니다.", signupResult);
        } catch (ServiceException e) {
            return getApiResponse("fail", e.getMessage(), "");
        } catch (Exception e) {
            return getApiResponse("fail", "회원가입에 실패하였습니다.", "");
        }
    }

    @ApiOperation(value="로그인", notes="userId와 password로 로그인, jwt token 발급")
    @PostMapping("/v1/login")
    public ApiResponseModel login(@RequestBody @ApiParam(value="로그인할 때 필요한 회원 정보", required = true) @Valid MemberLoginDto member) {
        try {
            TokenInfoModel loginResult = memberService.login(member);
            return getApiResponse("success", "로그인에 성공하였습니다.", loginResult);
        } catch (ServiceException e) {
            return getApiResponse("fail", e.getMessage(), "");
        } catch (Exception e) {
            return getApiResponse("fail", "로그인에 실패하였습니다.", "");
        }
    }

    @ApiOperation(value="내 정보 보기", notes="jwt token을 받아 권한이 있는 회원만 본인의 정보 열람 가능")
    @ApiImplicitParams({
            @ApiImplicitParam(name="X-AUTH-TOKEN", value="로그인 성공 후 발급 받은 token", dataType="String", paramType="header", required=true)
    })
    @PostMapping("/v1/me")
    public ApiResponseModel me(HttpServletRequest request) {
        String jwtToken = jwtTokenProvider.resolveToken(request);

        // token 확인 후 유효한 token일 때
        if(jwtTokenProvider.validateToken(jwtToken)) {
            String userId = jwtTokenProvider.getUserPk(jwtToken);
            MemberDto member = memberDao.getMemberInfo(userId);

            if(member == null) return getApiResponse("fail", "회원 정보 조회에 실패하였습니다.", "");
            else return getApiResponse("success", "", member);
        }
        // token이 유효하지 않을 때
        else {
            return getApiResponse("fail", "token이 유효하지 않습니다.", "");
        }
    }

    @ApiOperation(value="이름을 통한 회원 검색", notes="검색어를 받아 해당 글자가 포함된 이름을 가진 회원의 아이디와 이름 열람")
    @PostMapping("/v1/search")
    public ApiResponseModel search(@ApiParam(value="검색할 이름", required = true) String name) {
        List<MemberDto> members = memberDao.getSearchName(name);

        if(members == null) return getApiResponse("fail", "회원 조회에 실패하였습니다.", "");
        else if(members.isEmpty()) return getApiResponse("success", "조회된 회원이 없습니다.", "");
        else return getApiResponse("success", "", members);
    }
}
