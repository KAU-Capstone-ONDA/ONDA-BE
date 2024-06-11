package com.capstone.onda.domain.member.service;

import com.capstone.onda.domain.hotel.entity.Hotel;
import com.capstone.onda.domain.hotel.repository.HotelRepository;
import com.capstone.onda.domain.hotel.util.HotelMapper;
import com.capstone.onda.domain.member.dto.request.MemberSignUpRequest;
import com.capstone.onda.domain.member.dto.response.MemberResponse;
import com.capstone.onda.domain.member.entity.Member;
import com.capstone.onda.domain.member.exception.AlreadyExistEmailException;
import com.capstone.onda.domain.member.exception.InvalidHotelException;
import com.capstone.onda.domain.member.exception.InvalidMemberException;
import com.capstone.onda.domain.member.exception.InvalidTokenException;
import com.capstone.onda.domain.member.repository.MemberAuthRepository;
import com.capstone.onda.domain.member.repository.MemberRepository;
import com.capstone.onda.domain.member.util.MemberMapper;
import com.capstone.onda.global.exception.ErrorCode;
import com.capstone.onda.global.security.dto.RefreshToken;
import jakarta.annotation.PostConstruct;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberAuthRepository memberAuthRepository;
    private final MemberRepository memberRepository;
    private final HotelRepository hotelRepository;

    @Value("${kakao.key}")
    private String kakao_key;
    @Value("${kakao.base-url}")
    private String kakao_base_url;

    private HttpEntity<String> httpEntity;

    @PostConstruct
    protected void init() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, kakao_key);
        httpEntity = new HttpEntity<>(headers);
    }

    @Transactional
    public MemberResponse signUp(MemberSignUpRequest memberSignUpRequest) {
        if (memberRepository.findByUserEmail(memberSignUpRequest.email()).isPresent()) {
            throw new AlreadyExistEmailException(ErrorCode.ALREADY_EXIST_ID_EXCEPTION);
        }
        if (isPlaceExist(memberSignUpRequest)) {
            Hotel savedHotel = hotelRepository.save(HotelMapper.toHotel(memberSignUpRequest));
            return MemberMapper.toMemberResponse(memberRepository.save(
                MemberMapper.toMember(memberSignUpRequest, savedHotel)));
        } else {
            throw new InvalidHotelException(ErrorCode.INVALID_HOTEL_EXCEPTION);
        }
    }

    private boolean isPlaceExist(MemberSignUpRequest memberSignUpRequest) {
        URI checkURI = UriComponentsBuilder.fromHttpUrl(kakao_base_url)
            .queryParam("query", memberSignUpRequest.hotelName())
            .encode(StandardCharsets.UTF_8)
            .build().toUri();

        Assert.notNull(memberSignUpRequest.hotelName(), "query");
        ResponseEntity<String> hotelResponse = new RestTemplate().exchange(checkURI, HttpMethod.GET,
            httpEntity, String.class);

        JSONObject hotelObject = new JSONObject(hotelResponse.getBody().toString());
        JSONArray hotelArray = hotelObject.getJSONArray("documents");
        if (hotelArray.length() > 0) {
            for (int i = 0; i < 10; i++) {
                JSONObject candidateObject = hotelArray.getJSONObject(i);
                String candidateAddress = candidateObject.getString("address_name");
                if (candidateAddress.contains(memberSignUpRequest.region())
                    && candidateAddress.contains(memberSignUpRequest.city())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Transactional
    public void logout(String accessToken) {
        RefreshToken refreshToken = memberAuthRepository.findRefreshByAccessToken(
            accessToken).orElseThrow(() -> new InvalidTokenException(
            ErrorCode.INVALID_TOKEN));
        memberAuthRepository.delete(refreshToken);
    }

    @Transactional
    public void saveTokenInfo(String email, String accessToken, String refreshToken) {
        memberAuthRepository.save(new RefreshToken(email, accessToken, refreshToken));
    }

    public Member validateMember(String userEmail) {
        return memberRepository.findByUserEmail(userEmail)
            .orElseThrow(() -> new InvalidMemberException(ErrorCode.INVALID_MEMBER_EXCEPTION));
    }
}
