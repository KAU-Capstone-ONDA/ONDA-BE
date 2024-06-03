package com.capstone.onda.domain.member.service;

import com.capstone.onda.domain.member.dto.request.MemberSignUpRequest;
import com.capstone.onda.domain.member.dto.response.MemberResponse;
import com.capstone.onda.domain.member.exception.AlreadyExistEmailException;
import com.capstone.onda.domain.member.exception.InvalidTokenException;
import com.capstone.onda.domain.member.repository.MemberAuthRepository;
import com.capstone.onda.domain.member.repository.MemberRepository;
import com.capstone.onda.domain.member.util.MemberMapper;
import com.capstone.onda.global.exception.ErrorCode;
import com.capstone.onda.global.security.dto.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberAuthRepository memberAuthRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse signUp(MemberSignUpRequest memberSignUpRequest) {
        if (memberRepository.findByUserEmail(memberSignUpRequest.email()).isPresent()) {
            throw new AlreadyExistEmailException(ErrorCode.ALREADY_EXIST_ID_EXCEPTION);
        }
        return MemberMapper.toMemberResponse(memberRepository.save(
            MemberMapper.toMember(memberSignUpRequest)));
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
}
