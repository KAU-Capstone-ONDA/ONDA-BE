package com.capstone.onda.global.security.service;

import com.capstone.onda.domain.member.entity.Member;
import com.capstone.onda.domain.member.repository.MemberRepository;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    // 회원가입 작업
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        //1. oAuth2User 정보를 가져온다.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //2. RegistrationId 가져오기 (third-party id - kakao, naver)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //3. userNameAttributeName 가져오기 (yml - id, response)
        String userNameAttributeName = userRequest.getClientRegistration()
            .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //로그로 확인
        log.info("registrationId = {}", registrationId);
        log.info("userNameAttributeName = {}", userNameAttributeName);

        //4. 유저 정보 oAuth2Attribute 객체 생성
        OAuthAttribute oAuth2Attribute = OAuthAttribute.of(registrationId, userNameAttributeName,
            oAuth2User.getAttributes());

        //5. oAuth2Attribute의 속성 값들을 map으로 반환받는다.
        Map<String, Object> memberAttribute = oAuth2Attribute.convertToMap();

        String email = (String) memberAttribute.get("email");

        Optional<Member> findMember = memberRepository.findByUserEmail(email);

        if (findMember.isEmpty()) {
            memberAttribute.put("exist", false);
            return new DefaultOAuth2User(
                Collections.singleton(
                    new SimpleGrantedAuthority("ROLE_USER")),
                memberAttribute, "email");
        } else {
            memberAttribute.put("exist", true);
            return new DefaultOAuth2User(
                Collections.singleton(
                    new SimpleGrantedAuthority(findMember.get().getUserRole().toString())),
                memberAttribute, "email");
        }
    }

    // v1 회원 가입 로직
    private Member saveOrUpdate(OAuthAttribute oAuth2Attribute) {
        Member member = memberRepository.findByUserEmail(oAuth2Attribute.getEmail())
            // 회원 정보 업데이트
            .map(entity -> entity.update(oAuth2Attribute.getNickname(), oAuth2Attribute.getEmail()))
            // 가입되지 않은 사용자 => Member Entity 생성
            .orElse(oAuth2Attribute.toEntity());
        return memberRepository.save(member);
    }
}