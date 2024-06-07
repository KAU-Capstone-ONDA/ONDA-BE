
package com.capstone.onda.domain.member.dto.request;

import com.capstone.onda.domain.member.validation.ValidationGroups.NotBlankGroup;
import com.capstone.onda.domain.member.validation.ValidationGroups.SizeGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public record MemberSignUpRequest(
    @NotBlank(groups = NotBlankGroup.class)
    String email,

    @NotBlank(groups = NotBlankGroup.class)
    String name,

    @NotBlank(groups = NotBlankGroup.class)
    String provider,

    @NotBlank(groups = NotBlankGroup.class)
    @Size(max = 20, message = "이름의 길이는 20자 이내여야 합니다.", groups = SizeGroup.class)
    String hotelName,

    @NotBlank(groups = NotBlankGroup.class)
    @Size(max = 10, message = "시/도의 길이는 10자 이내여야 합니다.", groups = SizeGroup.class)
    String region,

    @NotBlank(groups = NotBlankGroup.class)
    @Size(max = 20, message = "시/군/구의 길이는 20자 이내여야 합니다.", groups = SizeGroup.class)
    String city
) {

    @Builder
    public MemberSignUpRequest(String email, String name, String provider, String hotelName,
        String region, String city) {
        this.email = email;
        this.name = name;
        this.provider = provider;
        this.hotelName = hotelName;
        this.region = region;
        this.city = city;
    }
}

