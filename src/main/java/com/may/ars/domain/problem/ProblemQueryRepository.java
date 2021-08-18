package com.may.ars.domain.problem;

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

    public List<Problem> findAllByTag(String tagName, Pageable page){
        return queryFactory
                .selectFrom(problem)
                .join(problemTag).on(problemTag.problem.eq(problem))
                .where(problemTag.tag.tagName.eq(tagName))
                .orderBy(problem.modifiedDate.desc())
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .fetch();
    }

}
