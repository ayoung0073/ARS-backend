package com.may.ars.domain.problem;

import com.may.ars.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    List<Problem> findAllByWriterIdOrderByCreatedDateDesc(Long id);

    Optional<Problem> findProblemByIdAndWriter(Long id, Member writer);

    void deleteProblemById(Long id);

    List<Problem> findAllByNotificationDate(LocalDate date);
}
