package com.may.ars.service;

import com.may.ars.dto.MemberDto;
import com.may.ars.enums.RoleType;
import com.may.ars.model.entity.member.Member;
import com.may.ars.model.entity.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional
    public Long saveMember(MemberDto memberDto) {
        Member member = Member.builder()
                .email(memberDto.getEmail())
                .roleType(RoleType.USER)
                .socialType(memberDto.getSocialType())
                .nickname(memberDto.getNickname())
                .build();

        memberRepository.save(member);
        return member.getId();
    }

}
