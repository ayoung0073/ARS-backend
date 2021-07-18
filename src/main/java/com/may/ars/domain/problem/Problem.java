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
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Problem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", updatable = false)
    private Member writer;

    @Column
    private String title;

    private String link = "";

    private int step;

    private LocalDate notificationDate;

    @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"problem"})
    @OrderBy("createdDate desc")
    private List<Review> reviewList;

    @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"problem"}) // X -> 무한참조
    private List<ProblemTag> tagList;
}
