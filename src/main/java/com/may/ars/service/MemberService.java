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
    public List<Member> findAllBySlackIdNull() {
        return memberRepository.findAllBySlackIdNull();
    }

    @Transactional
    public void saveMember(Member member) {
        memberRepository.save(member);
    }

}
