package com.may.ars.domain.review;

import com.may.ars.domain.BaseEntity;
import com.may.ars.domain.problem.Problem;
import com.may.ars.mapper.Default;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Entity
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", updatable = false)
    @Setter
    private Problem problem;

    @Setter
    @Lob
    @Type(type = "text")
    private String content;

    @Default
    @Builder
    public Review(Problem problem, String content) {
        this.problem = problem;
        this.content = content;
    }

}
