package com.may.ars.domain.problem;

import com.may.ars.domain.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    List<Problem> findByOrderByModifiedDateDesc(Pageable pageable); // 최신 업데이트순

    // 업데이트 날짜가 같은 경우를 고려해 (포함되지 않아야 할) cursorId 추가
    List<Problem> findByModifiedDateBeforeAndIdNotOrderByModifiedDateDesc(LocalDateTime modifiedDate, Long id, Pageable pageable); // 커서 기반 페이징 (업데이트순)

    Optional<Problem> findProblemByIdAndWriter(Long id, Member writer);

    void deleteProblemById(Long id);

    List<Problem> findAllByNotificationDate(LocalDate date);

    List<Problem> findAllByStepOrderByModifiedDateDesc(int step, Pageable pageable);

    List<Problem> findByIdLessThanAndStepOrderByIdDesc(Long id, int step, Pageable pageable); // 커서 기반 페이징

}
