package com.may.ars.model.entity.problem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.may.ars.model.entity.BaseEntity;
import com.may.ars.model.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member writer;

    @Column
    private String title;

    private String link;

    @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"problem"})
    private List<Review> reviewList;

//    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    private List<Tag> tagList;
}
