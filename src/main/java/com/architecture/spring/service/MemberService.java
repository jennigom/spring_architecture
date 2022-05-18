package com.architecture.spring.service;

import com.architecture.spring.dao.MemberDao;
import com.architecture.spring.dto.MemberDto;
import com.architecture.spring.dto.MemberSignUpDto;
import com.architecture.spring.dto.MemberLoginDto;
import com.architecture.spring.entity.MemberEntity;
import com.architecture.spring.model.response.TokenInfoModel;
import com.architecture.spring.repository.MemberRepository;
import com.architecture.spring.security.AES256;
import com.architecture.spring.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * member 관련 처리를 하는 service
 * @author JENNI
 * @version 1.1
 * @since 2022.05.17
 */

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    // 회원가입
    @Transactional(rollbackFor = Exception.class)
    public MemberDto signup(MemberSignUpDto member) throws Exception { // throws ServiceException
        MemberDto memberDto = null;
        // password 암호화: BCrypt Hashing
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        // 주민등록번호 암호화: AES256
        member.setRegNo((new AES256()).encrypt(member.getRegNo()));

        // 회원 Id 중복 체크
        if (!memberDao.validateUserId(member.getUserId())) {
//            memberDto = MemberDto.builder()
//                    .userId(member.getUserId())
//                    .build();
            //return apiResponseService.getApiResponse("fail", "이미 가입된 Id 입니다.", memberDto);
        }
        // 주민등록번호 중복 체크
        else if (!memberDao.validateRegNo(member.getRegNo())) {
            //return apiResponseService.getApiResponse("fail", "이미 가입된 주민등록번호 입니다.", "");
        }
        // 가입 가능한 정보
        else {
            MemberEntity newMember = memberDao.save(MemberEntity.builder()
                    .userId(member.getUserId())
                    .password(member.getPassword())
                    .name(member.getName())
                    .regNo(member.getRegNo())
                    .build());

            memberDto = MemberDto.builder()
                    .userId(newMember.getUserId())
                    .build();

            //return apiResponseService.getApiResponse("success", "", memberDto);
        }

        return memberDto;
    }

    // 로그인
    public TokenInfoModel login(MemberLoginDto loginInfo) {
        try {
            // 전달 받은 Id로 회원 조회
            Optional<MemberEntity> memberEntity = memberRepository.findById(loginInfo.getUserId());

            // 일치하는 Id가 없을 때
            if(memberEntity.isEmpty()) {
                return null;
                //return apiResponseService.getApiResponse("fail", "가입되지 않은 Id 입니다.", "");
            }
            // 일치하는 Id가 있을 때
            else {
                MemberEntity member = memberEntity.get();
                // 비밀번호가 틀렸을 때
                if (!passwordEncoder.matches(loginInfo.getPassword(), member.getPassword())) {
                    return null;
                    //return apiResponseService.getApiResponse("fail", "잘못된 비밀번호입니다.", "");
                }
                // 비밀번호가 일치했을 때
                else {
                    // 로그인 성공 시 token 발행 후 결과로 전송할 TokenInfoDto에 저장
                    TokenInfoModel jwtToken = TokenInfoModel.builder()
                            .userId(member.getUserId())
                            .token(jwtTokenProvider.createToken(member.getUserId()))
                            .build();
                    return jwtToken;
                    //return apiResponseService.getApiResponse("success", "", jwtToken);
                }
            }
        } catch (Exception e) {
            return null;
            //return apiResponseService.getApiResponse("fail", "로그인에 실패하였습니다.", "");
        }
    }

//    // 회원 정보 가져오기
//    public MemberDto getMemberInfo(String userId) {
//        try {
//            MemberEntity member = memberRepository.findById(userId)
//                    .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 Id 입니다."));
//
//            return MemberDto.builder()
//                    .userId(member.getUserId())
//                    .name(member.getName())
//                    .regNo((new AES256()).decrypt(member.getRegNo()))
//                    .build();
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    // 회원 정보 검색
//    public List<MemberDto> getSearchName(String name) {
//        try {
//            List<MemberEntity> members = memberRepository.findByNameContaining(name);
//            List<MemberDto> returnMembers = new ArrayList<>();
//
//            for (MemberEntity mem: members) {
//                MemberDto member = MemberDto.builder()
//                        .userId(mem.getUserId())
//                        .name(mem.getName())
//                        .build();
//                returnMembers.add(member);
//            }
//
//            return returnMembers;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    // 회원 Id 중복 체크
//    public boolean validateUserId(String userId) {
//        Optional<MemberEntity> member = memberRepository.findById(userId);
//        if(member.isEmpty()) return true;
//        else return false;
//    }
//
//    // 회원 주민등록번호 중복 체크
//    public boolean validateRegNo(String regNo) {
//        Optional<MemberEntity> member = memberRepository.findByRegNo(regNo);
//        if(member.isEmpty()) return true;
//        else return false;
//    }
//
//    public MemberEntity save(MemberEntity member) {
//        return memberRepository.save(member);
//    }
}
