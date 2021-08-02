package com.may.ars.service;

import com.may.ars.domain.guest.GuestBook;
import com.may.ars.domain.guest.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GuestBookService {

    private final GuestBookRepository guestBookRepository;

    @Transactional(readOnly = true)
    public List<GuestBook> getGuestBookList() {
        return guestBookRepository.findAll();
    }

    @Transactional
    public void saveGuestBook(GuestBook guestBook) {
        guestBookRepository.save(guestBook);
    }
}
