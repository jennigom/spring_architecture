package com.architecture.spring.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * spring security에서 사용되는 User에 추가적으로 필요한 파라미터를 추가하여 커스텀한 class
 * @author JENNI
 * @version 1.0
 * @since 2022.05.09
 */

@Getter
@Setter
@ToString
public class UserCustom extends User {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    // 추가할 파라미터
    private String name;

    public UserCustom(String username, String password, String name, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.name = name;
    }
}