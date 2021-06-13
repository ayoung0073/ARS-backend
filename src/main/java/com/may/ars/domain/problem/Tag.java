package com.may.ars.domain.problem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String tagName;

//    @OneToMany(mappedBy = "problem")
//    private List<Problem> problemList;

    public Tag(String tagName) {
        this.tagName = tagName;
    }
}
