package com.may.ars.domain.problem;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.may.ars.domain.problem.QProblem.problem;
import static com.may.ars.domain.problem.QProblemTag.problemTag;

import java.util.List;

@Repository
public class ProblemQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ProblemQueryRepository(JPAQueryFactory queryFactory) {
        super(Problem.class);
        this.queryFactory = queryFactory;
    }

    public List<Problem> findAllByTag(String tagName, Long cursorId, Pageable page){
        // 1. id < 파라미터를 첫 페이지에선 사용하지 않기 위한 동적 쿼리
        BooleanBuilder dynamicLtId = new BooleanBuilder();

        if (cursorId != 0L) {
            dynamicLtId.and(problem.id.lt(cursorId));
        }

        return queryFactory
                .selectFrom(problem)
                .join(problemTag).on(problemTag.problem.eq(problem))
                .where(dynamicLtId.and(problemTag.tag.tagName.eq(tagName)))
                .orderBy(problem.id.desc())
                .limit(page.getPageSize())
                .fetch();
    }

}
