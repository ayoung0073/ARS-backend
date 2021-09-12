package com.may.ars.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM review JOIN problem p on  review_id = :reviewId AND p.member_id = :memberId", nativeQuery = true)
    Optional<Review> findReviewByIdAndMemberId(Long reviewId, Long memberId);
}
