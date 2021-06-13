package com.may.ars.controller.api;

import com.may.ars.domain.member.Member;
import com.may.ars.dto.ResponseDto;
import com.may.ars.dto.problem.ProblemRegisterDto;
import com.may.ars.domain.problem.Problem;
import com.may.ars.mapper.ProblemMapper;
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
    public ResponseEntity<?> saveProblem(@RequestBody ProblemRegisterDto registerDto) {
        Member member = MemberContext.currentMember.get();
        problemService.registerProblem(problemMapper.toEntity(registerDto, member), registerDto);
        log.info(registerDto.toString());

        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, "문제 등록 성공");
        return ResponseEntity.ok().body(response);
    }
}
