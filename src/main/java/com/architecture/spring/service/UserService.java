package com.architecture.spring.service;

import com.architecture.spring.entity.MemberEntity;
import com.architecture.spring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * spring security에서 사용되는 UserDetailsService를 상속 받아 프로젝트에 맡게 커스텀한 class
 * @author JENNI
 * @version 1.0
 * @since 2022.05.09
 */

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> memberEntityWrapper = memberRepository.findById(username);

        if(memberEntityWrapper.isEmpty()) throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");

        MemberEntity member = memberEntityWrapper.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        return new UserCustom(member.getUsername(), member.getPassword(), member.getName(), authorities);
    }
}
