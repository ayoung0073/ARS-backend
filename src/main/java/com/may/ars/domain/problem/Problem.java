package com.may.ars.domain.problem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.may.ars.domain.BaseEntity;
import com.may.ars.domain.member.Member;
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
public class Problem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member writer;

    @Column
    private String title;

    private String link;

    @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"problem"})
    @OrderBy("createdDate desc")
    private List<Review> reviewList;

    @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"problem"}) // X -> 무한참조
    private List<ProblemTag> tagList;
}
