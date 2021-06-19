package com.may.ars.controller.api;

import com.may.ars.domain.member.Member;
import com.may.ars.dto.ResponseDto;
import com.may.ars.dto.problem.ProblemRequestDto;
import com.may.ars.domain.problem.Problem;
import com.may.ars.mapper.ProblemMapper;
import com.may.ars.response.SuccessMessage;
import com.may.ars.service.ProblemService;
import com.may.ars.utils.AuthCheck;
import com.may.ars.utils.MemberContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/problems")
public class ProblemApiController {

    private final ProblemMapper problemMapper;
    private final ProblemService problemService;

    @AuthCheck
    @GetMapping("")
    public ResponseEntity<?> getProblemList() {
        Member member = MemberContext.currentMember.get();
        List<Problem> problemList = problemService.getProblemListByMember(member);

        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, "문제 가져오기", problemList);
        return ResponseEntity.ok().body(response);
    }

    @AuthCheck
    @PostMapping("")
    public ResponseEntity<?> saveProblem(@RequestBody ProblemRequestDto requestDto) {
        Member member = MemberContext.currentMember.get();
        problemService.registerProblem(problemMapper.toEntity(requestDto, member), requestDto);

        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, SuccessMessage.SUCCESS_REGISTER_PROBLEM);
        return ResponseEntity.ok().body(response);
    }

    @AuthCheck
    @PutMapping("/{problemId}")
    public ResponseEntity<?> updateProblem(@PathVariable Long problemId, @RequestBody ProblemRequestDto requestDto) {
        Member member = MemberContext.currentMember.get();
        problemService.updateProblem(problemMapper.toEntity(problemId, requestDto, member));

        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, SuccessMessage.SUCCESS_UPDATE_PROBLEM);
        return ResponseEntity.ok().body(response);
    }

    @AuthCheck
    @DeleteMapping("/{problemId}")
    public ResponseEntity<?> detailPage(@PathVariable Long problemId) {
        Member member = MemberContext.currentMember.get();
        problemService.deleteProblem(problemId, member);

        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, SuccessMessage.SUCCESS_DELETE_PROBLEM);
        return ResponseEntity.ok().body(response);
    }



}
