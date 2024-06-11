package com.capstone.onda.global.security.handler;

import com.capstone.onda.global.security.dto.CustomOAuth2User;
import com.capstone.onda.global.security.dto.GeneratedToken;
import com.capstone.onda.global.security.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private static final String LOGIN_URI = "http://localhost:3000/oauthlogin";
    private static final String SIGNUP_URI = "http://localhost:3000/signup";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        //1. oAuth2User로 캐스팅하여 인증된 사용자의 정보를 가져온다.
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        //2. oAuth2User의 정보들을 가져온다.
        String email = customOAuth2User.getEmail();

        String provider = customOAuth2User.getProvider();

        String nickname = customOAuth2User.getName();

        boolean isExist = customOAuth2User.getExist();

        //3. 로그인한 회원 존재의 여부를 가져온다.
        String role = authentication.getAuthorities().stream()
            .findFirst()
            .orElseThrow(IllegalAccessError::new)
            .getAuthority();

        if (isExist) {
            GeneratedToken generatedToken = jwtUtil.generateToken(email, role);
            String loginRedirectUrl = UriComponentsBuilder.fromUriString(LOGIN_URI)
                .queryParam("accessToken", generatedToken.accessToken())
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
            log.info(provider + "회원 access Token redirect 준비");
            getRedirectStrategy().sendRedirect(request, response, loginRedirectUrl);
        } else {
            String signupRedirectUrl = UriComponentsBuilder.fromUriString(SIGNUP_URI)
                .queryParam("email", email)
                .queryParam("nickname", nickname)
                .queryParam("provider", provider)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
            log.info(provider + "회원 회원가입 준비");
            getRedirectStrategy().sendRedirect(request, response, signupRedirectUrl);
        }
    }
}