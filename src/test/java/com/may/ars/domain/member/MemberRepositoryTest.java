package com.may.ars.domain.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 멤버_저장_테스트() {
        // given
        Member member = Member.builder()
                .email("ayong0310@naver. com")
                .nickname("문아영")
                .build();

        // when
        memberRepository.save(member);

        // then
        assertThat(memberRepository.findByEmail(member.getEmail()).isPresent()).isEqualTo(true);
    }
}