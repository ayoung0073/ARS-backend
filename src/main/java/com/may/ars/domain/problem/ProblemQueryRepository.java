package com.may.ars.domain.problem;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.may.ars.domain.problem.QProblem.problem;
import static com.may.ars.domain.problem.QProblemTag.problemTag;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ProblemQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ProblemQueryRepository(JPAQueryFactory queryFactory) {
        super(Problem.class);
        this.queryFactory = queryFactory;
    }

    public List<Problem> findAllByTag(LocalDateTime modifiedDate, Long problemId, String tagName, int size) {
        return queryFactory
                .selectFrom(problem)
                .join(problemTag).on(problemTag.problem.eq(problem))
                .where(
                        beforeModifiedDateAndNotProblemId(modifiedDate, problemId),
                        problemTag.tag.tagName.eq(tagName)
                )
                .orderBy(problem.id.desc())
                .limit(size)
                .fetch();
    }

    private BooleanExpression beforeModifiedDateAndNotProblemId(LocalDateTime modifiedDate, Long problemId) {
        // id < 파라미터를 첫 페이지에선 사용하지 않기 위한 동적 쿼리
        if (modifiedDate == null) {
            return null; // BooleanExpression 자리에 null 이 반환되면 조건문에서 자동으로 제거
        }
        if (problemId.equals(0L)) {
            return problem.modifiedDate.before(modifiedDate);
        }
        return problem.modifiedDate.before(modifiedDate).and(problem.id.notIn(problemId));
    }

}