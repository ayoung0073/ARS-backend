package com.may.ars.domain.review;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

    public List<Review> search(String keyword, Long reviewId, int size) {
        return queryFactory
                .selectFrom(review)
                .where(
                        ltReviewId(reviewId),
                        review.content.containsIgnoreCase(keyword).or(review.problem.title.containsIgnoreCase(keyword))
                )
                .orderBy(review.id.desc())
                .limit(size)
                .fetch();
    }

    private BooleanExpression ltReviewId(Long reviewId) {
        if (reviewId.equals(0L)) {
            return null;
        }
        return review.id.lt(reviewId);
    }

}
