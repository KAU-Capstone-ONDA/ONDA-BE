package com.capstone.onda.domain.member.service;

import com.capstone.onda.domain.member.exception.InvalidTokenException;
import com.capstone.onda.domain.member.repository.MemberAuthRepository;
import com.capstone.onda.global.exception.ErrorCode;
import com.capstone.onda.global.security.dto.RefreshToken;
import com.capstone.onda.global.security.util.JwtUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberAuthService {

    private final JwtUtil jwtUtil;
    private final MemberAuthRepository memberAuthRepository;

    public String refreshAccessToken(String accessToken) {
        Optional<RefreshToken> tokenResponse = memberAuthRepository.findRefreshByAccessToken(
            accessToken);
        if (tokenResponse.isPresent() && jwtUtil.verifyToken(
            tokenResponse.get().getRefreshToken())) {
            RefreshToken token = tokenResponse.get();
            String newAccessToken = jwtUtil.generateAccessToken(token.getId(),
                jwtUtil.getRole(token.getRefreshToken()));
            token.updateAccessToken(newAccessToken);
            memberAuthRepository.save(token);
            return newAccessToken;
        } else {
            throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
        }
    }
}
