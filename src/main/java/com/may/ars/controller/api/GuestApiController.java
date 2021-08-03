package com.may.ars.controller.api;

import com.may.ars.dto.ResponseDto;
import com.may.ars.dto.guest.GuestRequestDto;
import com.may.ars.dto.guest.GuestResponseDto;
import com.may.ars.mapper.GuestMapper;
import com.may.ars.service.GuestBookService;
import com.may.ars.utils.auth.AuthCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.may.ars.common.message.SuccessMessage.*;
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
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, SUCCESS_GET_GUEST_LIST, guestBookList)
        );
    }

    @PostMapping("")
    public ResponseEntity<?> saveGuestBook(@RequestBody @Valid GuestRequestDto requestDto) {
        guestBookService.saveGuestBook(guestMapper.toEntity(requestDto));
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, SUCCESS_REGISTER_GUEST)
        );
    }

    @AuthCheck
    @DeleteMapping("/{guestId}")
    public ResponseEntity<?> deleteGuestBook(@PathVariable Long guestId) {
        guestBookService.deleteGuestBook(guestId);
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, SUCCESS_DELETE_GUEST)
        );
    }

}
