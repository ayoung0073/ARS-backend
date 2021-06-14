package com.may.ars.domain.problem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.may.ars.domain.BaseEntity;
import com.may.ars.domain.member.Member;
import com.may.ars.domain.review.Review;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Problem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member writer;

    @Setter
    @Column
    private String title;

    @Setter
    private String link;

    @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"problem"})
    @OrderBy("createdDate desc")
    private List<Review> reviewList;

    @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"problem"}) // X -> 무한참조
    private List<ProblemTag> tagList;
}
