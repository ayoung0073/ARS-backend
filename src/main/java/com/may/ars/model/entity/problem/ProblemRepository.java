package com.may.ars.model.entity.problem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    List<Problem> findAllByWriterIdOrderByCreatedDateDesc(Long id);
}
