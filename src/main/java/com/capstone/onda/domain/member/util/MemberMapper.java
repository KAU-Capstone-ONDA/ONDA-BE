package com.capstone.onda.domain.member.util;

import com.capstone.onda.domain.hotel.entity.Hotel;
import com.capstone.onda.domain.member.dto.request.MemberSignUpRequest;
import com.capstone.onda.domain.member.dto.response.MemberResponse;
import com.capstone.onda.domain.member.entity.Member;
import com.capstone.onda.domain.member.entity.ProviderType;
import com.capstone.onda.domain.member.entity.RoleType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMapper {

    public static Member toMember(MemberSignUpRequest memberSignUpRequest) {
        return Member.builder()
            .userEmail(memberSignUpRequest.email())
            .userNickName(memberSignUpRequest.name())
            .providerType(ProviderType.valueOf(memberSignUpRequest.provider()))
            .userRole(RoleType.USER)
            .hotel(Hotel.builder()
                .hotelName(memberSignUpRequest.hotelName())
                .region(memberSignUpRequest.region())
                .city(memberSignUpRequest.city())
                .build())
            .build();
    }

    public static MemberResponse toMemberResponse(Member member){
        return MemberResponse.builder()
            .id(member.getId())
            .email(member.getUserEmail())
            .name(member.getUserNickName())
            .hotelName(member.getHotel().getHotelName())
            .region(member.getHotel().getRegion())
            .city(member.getHotel().getCity())
            .build();
    }

}
