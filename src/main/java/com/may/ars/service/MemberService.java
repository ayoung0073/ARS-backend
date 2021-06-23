package com.may.ars.service;

import com.may.ars.dto.member.MemberDto;
import com.may.ars.enums.RoleType;
import com.may.ars.domain.member.Member;
import com.may.ars.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
                .socialId(memberDto.getSocialId())
                .build();

        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public List<Member> findAllBySlackIdNull() {
        return memberRepository.findAllBySlackIdNull();
    }

    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }

}
