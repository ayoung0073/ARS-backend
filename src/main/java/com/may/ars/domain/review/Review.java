package com.may.ars.domain.review;

import com.may.ars.domain.BaseEntity;
import com.may.ars.domain.problem.Problem;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "problem_id", updatable = false)
    @Setter
    private Problem problem;

    @Setter
    @Lob
    @Type(type = "text")
    private String content;

    @Setter
    private int step;
}
