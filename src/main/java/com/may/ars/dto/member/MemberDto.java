package com.may.ars.dto.member;

import com.may.ars.enums.RoleType;
import com.may.ars.enums.SocialType;
import com.may.ars.model.entity.member.Member;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberDto {

    private Long memberId;

    private String email;

    private RoleType roleType;

    private SocialType socialType;

    private String nickname;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public static MemberDto fromEntity(Member entity) {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberId(entity.getId());
        memberDto.setEmail(entity.getEmail());
        memberDto.setNickname(entity.getNickname());
        memberDto.setRoleType(entity.getRoleType());
        memberDto.setSocialType(entity.getSocialType());
        memberDto.setCreatedDate(entity.getCreatedDate());
        memberDto.setModifiedDate(entity.getModifiedDate());

        return memberDto;
    }

    public Member toEntity() {
        return Member.builder()
                .id(memberId)
                .email(email)
                .nickname(nickname)
                .roleType(roleType)
                .socialType(socialType)
                .build();
    }
}
