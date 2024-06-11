package com.capstone.onda.domain.member.controller;

import com.capstone.onda.domain.member.dto.request.MemberSignUpRequest;
import com.capstone.onda.domain.member.dto.response.MemberResponse;
import com.capstone.onda.domain.member.service.MemberService;
import com.capstone.onda.domain.member.validation.ValidationSequence;
import com.capstone.onda.global.common.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "member api", description = "member 관련 API")
@RestController
@RequestMapping("v1/member")
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;
    private final String AUTHORIZATION_HEADER = "Authorization";

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "회원가입 API", description = "로그인시, 비회원일 경우 회원가입을 진행하는 API 입니다.")
    public ResponseDTO<MemberResponse> signup(@Validated(ValidationSequence.class) @RequestBody
        MemberSignUpRequest memberSignUpRequest) {
        return ResponseDTO.res(memberService.signUp(memberSignUpRequest), "회원가입에 성공했습니다.");
    }

    @PostMapping("/token/logout")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "로그아웃 API", description = "서비스의 로그아웃 API 입니다.")
    public ResponseDTO<String> logout(
        @RequestHeader(AUTHORIZATION_HEADER) final String accessToken) {
        memberService.logout(accessToken);
        return ResponseDTO.res("로그아웃에 성공했습니다.");
    }

}
