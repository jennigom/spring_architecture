package com.architecture.spring.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 회원 테이블
 * @author JENNI
 * @version 1.0
 * @since 2022.05.09
 */

@Getter
@Builder
@Entity(name="member")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity implements UserDetails {
    // 회원 Id
    @Id
    @Column(length = 20)
    private String userId;

    // 비밀번호
    @NotNull
    @Column(length = 100)
    private String password;

    // 회원 이름
    @NotNull
    @Column(length = 60)
    private String name;

    // 주민등록번호
    @javax.validation.constraints.NotNull
    @Column(length = 30)
    private String regNo;

    // 사용자의 권한을 콜렉션 형태로 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : "".split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    // 비밀번호 반환
    @Override
    public String getPassword() {
        return password;
    }

    // username 반환 (unique한 값)
    @Override
    public String getUsername() {
        return userId;
    }

    // 계정 만료 여부 반환
    // 만료 -> false
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 여부 반환
    // 만료 -> false
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // password 만료 여부 반환
    // 만료 -> false
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용 가능 여부 반환
    // 사용 불가 -> false
    @Override
    public boolean isEnabled() {
        return true;
    }
}
