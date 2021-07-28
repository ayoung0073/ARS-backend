package com.may.ars.domain.problem;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.may.ars.domain.problem.QProblemTag.problemTag;

import java.util.List;

@Repository
public class TagQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public TagQueryRepository(JPAQueryFactory queryFactory) {
        super(ProblemTag.class);
        this.queryFactory = queryFactory;
    }

    public List<Tuple> findAllTagList() {
        return queryFactory
                .from(problemTag)
                .groupBy(problemTag.tag)
                .select(problemTag.tag.tagName, problemTag.tag.count())
                .fetch();
    }
}
