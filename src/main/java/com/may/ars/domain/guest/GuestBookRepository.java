package com.may.ars.domain.guest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long> {
    List<GuestBook> findAllByOrderByCreatedDateDesc(Pageable pageable);
}
