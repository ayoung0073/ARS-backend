package com.may.ars.service;

import com.may.ars.domain.review.Review;
import com.may.ars.domain.review.ReviewQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final ReviewQueryRepository reviewQueryRepository;

    public List<Review> search(String keyword, Long reviewId, int size) {
        return reviewQueryRepository.search(keyword, reviewId, size);
    }

}
