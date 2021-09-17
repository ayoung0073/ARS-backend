package com.may.ars.mapper;

import com.may.ars.domain.guest.GuestBook;
import com.may.ars.dto.guest.GuestRequestDto;
import com.may.ars.dto.guest.GuestResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class GuestMapperTest {

    @Autowired
    private GuestMapper guestMapper;

    @Test
    void toEntity_테스트() {
        // given
        final GuestRequestDto requestDto = GuestRequestDto.builder()
                .content("테스트")
                .nickname("닉네임")
                .build();
        // when
        final GuestBook guestBook = guestMapper.toEntity(requestDto);
        // then
        assertThat(guestBook.getContent(), is(requestDto.getContent()));
        assertThat(guestBook.getNickname(), is(requestDto.getNickname()));
    }

    @Test
    void toDto_테스트() {
        // given
        final GuestBook guestBook = GuestBook.builder()
                .content("테스트")
                .nickname("닉네임")
                .build();
        // when
        final GuestResponseDto responseDto = guestMapper.toDto(guestBook);
        // then
        assertThat(responseDto.getContent(), is(guestBook.getContent()));
        assertThat(responseDto.getNickname(), is(guestBook.getNickname()));
    }

}