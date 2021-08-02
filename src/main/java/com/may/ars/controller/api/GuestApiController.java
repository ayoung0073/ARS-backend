package com.may.ars.controller.api;

import com.may.ars.domain.guest.GuestBook;
import com.may.ars.dto.ResponseDto;
import com.may.ars.dto.guest.GuestRequestDto;
import com.may.ars.dto.guest.GuestResponseDto;
import com.may.ars.mapper.GuestMapper;
import com.may.ars.response.SuccessMessage;
import com.may.ars.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/guests")
public class GuestApiController {

    private final GuestBookService guestBookService;
    private final GuestMapper guestMapper;

    @GetMapping("")
    public ResponseEntity<?> getGuestBookList() {
        List<GuestResponseDto> guestBookList = guestBookService.getGuestBookList().stream()
                                                                                  .map(guestMapper::toDto)
                                                                                  .collect(toList());
        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, SuccessMessage.SUCCESS_GET_GUEST_LIST, guestBookList);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("")
    public ResponseEntity<?> saveGuestBook(@RequestBody GuestRequestDto requestDto) {
        guestBookService.saveGuestBook(guestMapper.toEntity(requestDto));
        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, SuccessMessage.SUCCESS_REGISTER_GUEST);
        return ResponseEntity.ok().body(response);
    }

}
