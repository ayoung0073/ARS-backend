package com.may.ars.domain.problem;

import com.may.ars.domain.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    List<Problem> findAllByOrderByIdDesc(Pageable pageable);

    List<Problem> findByIdLessThanOrderByIdDesc(Long id, Pageable pageable); // 커서 기반 페이징

    Optional<Problem> findProblemByIdAndWriter(Long id, Member writer);

    void deleteProblemById(Long id);

    List<Problem> findAllByNotificationDate(LocalDate date);

    List<Problem> findAllByStepOrderByModifiedDateDesc(int step, Pageable pageable);

    List<Problem> findByIdLessThanAndStepOrderByIdDesc(Long id, int step, Pageable pageable); // 커서 기반 페이징

}
