package com.may.ars.controller.api;

import com.may.ars.dto.member.MemberDto;
import com.may.ars.dto.ResponseDto;
import com.may.ars.dto.problem.ProblemRegisterDto;
import com.may.ars.model.entity.problem.Problem;
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
@RequestMapping("/api")
public class ProblemApiController {

    private final ProblemService problemService;

    @AuthCheck
    @GetMapping("/problems")
    public ResponseEntity<?> getProblemList() {
        MemberDto member = MemberContext.currentMember.get();
        List<Problem> problemList = problemService.getProblemListByMember(member);

        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, "문제 가져오기", problemList);
        return ResponseEntity.ok().body(response);
    }

    @AuthCheck
    @PostMapping("/problems")
    public ResponseEntity<?> saveProblem(@RequestBody ProblemRegisterDto registerDto) {
        MemberDto member = MemberContext.currentMember.get();

        log.info("글 등록");
        log.info("글 작성자 : " + member.getNickname());
        log.info("리뷰 : " + registerDto.getContent());
        log.info("복습 중요도 : " + registerDto.getStep());
        registerDto.setWriter(member.toEntity());
        problemService.registerProblem(registerDto);
        log.info(registerDto.toString());

        ResponseDto<?> response = ResponseDto.of(HttpStatus.OK, "문제 등록 성공");
        return ResponseEntity.ok().body(response);
    }
}
