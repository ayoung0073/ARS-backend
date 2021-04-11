package com.may.ars.dto;

import com.may.ars.enums.RoleType;
import com.may.ars.enums.SocialType;
import com.may.ars.model.entity.member.Member;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberDto {

    private long memberId;

    private String email;

    private RoleType roleType;

    private SocialType socialType;

    private String nickname;

    private String createdDate;

    private String modifiedDate;

    public static MemberDto fromEntity(Member entity) {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberId(entity.getId());
        memberDto.setEmail(entity.getEmail());
        memberDto.setNickname(entity.getNickname());
        memberDto.setRoleType(entity.getRoleType());
        memberDto.setSocialType(entity.getSocialType());
        memberDto.setCreatedDate(entity.getCreatedDate().toString());
        memberDto.setModifiedDate(entity.getModifiedDate().toString());

        return memberDto;
    }
}
