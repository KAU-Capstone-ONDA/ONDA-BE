package com.capstone.onda.domain.member.entity;

import com.capstone.onda.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("회원 식별자")
    private Long id;

    @Column(length = 40, nullable = false)
    @Comment("회원 이메일")
    private String userEmail;

    @Column(length = 15, nullable = false)
    @Comment("회원 닉네임")
    private String userNickName;

    @Enumerated(EnumType.STRING)
    @Comment("회원 역할")
    private RoleType userRole;

    @Builder
    public Member(Long id, String userEmail, String userNickName, RoleType userRole) {
        this.id = id;
        this.userEmail = userEmail;
        this.userNickName = userNickName;
        this.userRole = userRole;
    }

    public Member update(String userNickName, String userEmail) {
        this.userNickName = userNickName;
        this.userEmail = userEmail;
        return this;
    }
}
