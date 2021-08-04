package com.may.ars.domain.review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.may.ars.domain.review.QReview.review;

@Repository
public class ReviewQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ReviewQueryRepository(JPAQueryFactory queryFactory) {
        super(Review.class);
        this.queryFactory = queryFactory;
    }

    public List<Review> search(String keyword, Pageable page){
        return queryFactory
                .selectFrom(review)
                .where(review.content.containsIgnoreCase(keyword).or(review.problem.title.containsIgnoreCase(keyword)))
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .orderBy(review.problem.createdDate.desc())
                .fetch();
    }

}
