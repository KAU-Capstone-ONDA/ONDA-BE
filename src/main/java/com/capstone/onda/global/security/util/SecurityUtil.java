package com.capstone.onda.global.security.util;

import com.capstone.onda.global.security.dto.SecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class SecurityUtil {

    public static String getUserEmail() {
        return ((SecurityUser) (SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal())).email();
    }

    public static SecurityUser getUser() {
        return (SecurityUser) (SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal());
    }

}
