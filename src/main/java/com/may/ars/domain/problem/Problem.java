package com.may.ars.domain.problem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.may.ars.domain.BaseEntity;
import com.may.ars.domain.member.Member;
import com.may.ars.domain.review.Review;
import com.may.ars.mapper.Default;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Entity
public class Problem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", updatable = false)
    @JsonIgnore
    private Member writer;

    @Column
    private String title;

    private String link;

    private int step;

    private LocalDate notificationDate;

    @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"problem"})
    @OrderBy("createdDate desc")
    private List<Review> reviewList;

    @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"problem"}) // X -> 무한참조
    private List<ProblemTag> tagList;

    @Default
    @Builder
    public Problem(Member writer, String title, String link, int step, LocalDate notificationDate, List<Review> reviewList, List<ProblemTag> tagList) {
        this.writer = writer;
        this.link = link;
        this.title = title;
        this.step = step;
        this.notificationDate = notificationDate;
        this.reviewList = reviewList;
        this.tagList = tagList;
    }

}
