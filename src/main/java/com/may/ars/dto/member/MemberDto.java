package com.may.ars.dto.member;

import com.may.ars.enums.RoleType;
import com.may.ars.enums.SocialType;
import com.may.ars.domain.member.Member;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MemberDto {

    private Long memberId;

    private String email;

    private RoleType roleType;

    private SocialType socialType;

    private String nickname;

    private Long socialId;

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
        memberDto.setSocialId(entity.getSocialId());

        return memberDto;
    }
}
