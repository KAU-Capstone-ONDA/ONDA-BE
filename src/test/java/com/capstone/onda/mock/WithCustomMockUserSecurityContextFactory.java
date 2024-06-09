package com.capstone.onda.mock;

import com.capstone.onda.global.security.dto.SecurityUser;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithCustomMockUserSecurityContextFactory implements
    WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser customMockUser) {

        final SecurityContext context = SecurityContextHolder.createEmptyContext();

        final SecurityUser securityUser = SecurityUser.builder()
            .email(customMockUser.email())
            .nickname(customMockUser.nickname())
            .role(customMockUser.role())
            .build();

        final Authentication token = new UsernamePasswordAuthenticationToken(securityUser, " ",
            List.of(new SimpleGrantedAuthority("ROLE_USER")));

        context.setAuthentication(token);
        return context;
    }
}
