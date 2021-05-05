package com.may.ars.model.entity.problem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.may.ars.model.entity.BaseEntity;
import com.may.ars.model.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Lob
    private String content;

    private int step;
}
