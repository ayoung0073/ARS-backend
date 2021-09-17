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
        // id < 파라미터를 첫 페이지에선 사용하지 않기 위한 동적 쿼리
        if (reviewId.equals(0L)) {
            return null; // BooleanExpression 자리에 null 이 반환되면 조건문에서 자동으로 제거
        }
        return review.id.lt(reviewId);
    }

}
