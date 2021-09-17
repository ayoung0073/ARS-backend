package com.may.ars.service;

import com.may.ars.domain.guest.GuestBook;
import com.may.ars.domain.guest.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GuestBookService {

    private final GuestBookRepository guestBookRepository;

    @Transactional(readOnly = true)
    public List<GuestBook> getGuestBookList(Pageable page) {
        return guestBookRepository.findAllByOrderByCreatedDateDesc(page);
    }

    @Transactional(readOnly = true)
    public long getGuestBookCount() {
        return guestBookRepository.count();
    }

    @Transactional
    public void saveGuestBook(GuestBook guestBook) {
        log.info("[" + guestBook.getNickname() + "] " + guestBook.getContent());
        guestBookRepository.save(guestBook);
    }

    @Transactional
    public void deleteGuestBook(Long guestId) {
        guestBookRepository.deleteById(guestId);
    }
}
