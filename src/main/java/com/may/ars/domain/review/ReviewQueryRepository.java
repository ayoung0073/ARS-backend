package com.may.ars.domain.review;

import com.may.ars.dto.problem.response.ProblemOnlyDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.may.ars.domain.problem.QProblemTag.problemTag;
import static com.may.ars.domain.review.QReview.review;

@Repository
public class ReviewQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ReviewQueryRepository(JPAQueryFactory queryFactory) {
        super(Review.class);
        this.queryFactory = queryFactory;
    }

    public List<ProblemOnlyDto> getReviewList(Long reviewId, int size) {
        return queryFactory
                .from(review)
                .select(Projections.fields(
                        ProblemOnlyDto.class,
                        review.id.max().as("reviewId"),
                        review.problem.id.as("problemId"),
                        review.problem.title.as("title"),
                        review.problem.link.as("link"),
                        review.problem.step.as("step"),
                        review.problem.notificationDate.as("notificationDate")
                        // review.problem.tagList.as("tagList") TODO
                        )
                )
                .where(
                        ltReviewId(reviewId)
                )
                .orderBy(review.id.max().desc())
                .groupBy(review.problem)
                .limit(size)
                .fetch();
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

    public List<Review> findAllByTag(String tagName, Long reviewId, Pageable page) {
        return queryFactory
                .selectFrom(review)
                .join(problemTag).on(problemTag.problem.eq(review.problem))
                .where(
                        ltReviewId(reviewId),
                        problemTag.tag.tagName.eq(tagName)
                )
                .orderBy(review.id.desc())
                .limit(page.getPageSize())
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
