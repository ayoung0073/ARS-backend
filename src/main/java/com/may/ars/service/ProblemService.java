package com.may.ars.service;

import com.may.ars.common.advice.exception.EntityNotFoundException;
import com.may.ars.domain.member.Member;
import com.may.ars.domain.review.ReviewRepository;
import com.may.ars.dto.problem.request.ProblemRequestDto;
import com.may.ars.domain.problem.*;
import com.may.ars.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final ProblemQueryRepository problemQueryRepository;
    private final TagRepository tagRepository;
    private final ReviewRepository reviewRepository;
    private final ProblemTagRepository problemTagRepository;

    private final ReviewMapper reviewMapper;

    @Transactional(readOnly = true)
    public List<Problem> getProblemListByStepOrTag(int step, String tagName, Pageable page) {
        if (step == 0 && tagName.isBlank()) {
            return getProblemList(page);
        }
        else if (step == 0) {
            return getProblemListByTagName(tagName, page);
        }
        else if (tagName.isBlank()){
            return getProblemListByStep(step, page);
        }
        else {
            return getProblemList(page);
        }
    }

    @Transactional(readOnly = true)
    public Problem getProblemById(Long problemId) {
        return problemRepository.findById(problemId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public long getProblemCount() {
        return problemRepository.count();
    }

    @Transactional
    public Long registerProblem(Problem problem, ProblemRequestDto registerDto) {
        problemRepository.save(problem);
        reviewRepository.save(reviewMapper.toEntity(problem, registerDto));

        List<ProblemTag> problemTagList = new ArrayList<>();

        // TODO 리팩토링 하고 싶다.
        for (String tagName : registerDto.getTagList()) {
            Optional<Tag> tagOptional = tagRepository.findByTagName(tagName);
            Tag tag;
            if (tagOptional.isEmpty()) { // 태그 새로 등록해야 하는 경우
                tag = new Tag(tagName);
                tagRepository.save(tag);
            } else {
                tag = tagOptional.get();
            }
            problemTagList.add(new ProblemTag(problem, tag));
        }
        problemTagRepository.saveAll(problemTagList);
        return problem.getId();
    }

    @Transactional
    public void updateProblem(Problem problem) {
        Problem updateProblem = checkValidUser(problem.getId(), problem.getWriter());
        updateProblem.setNotificationDate(problem.getNotificationDate());
        problemRepository.save(updateProblem);
    }

    @Transactional
    public void updateStep(Long problemId, Member member, int step) {
        Problem updateProblem = checkValidUser(problemId, member);
        updateProblem.setStep(step);
        problemRepository.save(updateProblem);
    }

    @Transactional
    public void updateNotificationDate(Long problemId, Member member, LocalDate notificationDate) {
        Problem updateProblem = checkValidUser(problemId, member);
        updateProblem.setNotificationDate(notificationDate);
        problemRepository.save(updateProblem);
    }

    @Transactional
    public void deleteProblem(Long problemId, Member member) {
        checkValidUser(problemId, member);
        problemRepository.deleteById(problemId);
    }

    public List<Problem> getProblemList(Pageable page) {
        return problemRepository.findAllByOrderByCreatedDateDesc(page);
    }

    public List<Problem> getProblemListByStep(int step, Pageable page) {
        return problemRepository.findAllByStep(step, page);
    }

    public List<Problem> getProblemListByTagName(String tagName, Pageable page) {
        if (tagName == null) {
            return getProblemList(page);
        }
        return problemQueryRepository.findAllByTag(tagName, page);
    }

    private Problem checkValidUser(Long problemId, Member member) {
        return problemRepository.findProblemByIdAndWriter(problemId, member).orElseThrow(EntityNotFoundException::new);
    }
}
