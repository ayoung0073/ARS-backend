package com.may.ars.controller.api;

import com.may.ars.domain.member.Member;
import com.may.ars.dto.ResponseDto;
import com.may.ars.dto.problem.request.ProblemRequestDto;
import com.may.ars.dto.problem.request.ProblemStepUpdateDto;
import com.may.ars.dto.problem.response.ProblemOnlyDto;
import com.may.ars.mapper.ProblemMapper;
import com.may.ars.common.message.SuccessMessage;
import com.may.ars.service.ProblemService;
import com.may.ars.utils.auth.AuthCheck;
import com.may.ars.utils.auth.MemberContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.may.ars.common.message.SuccessMessage.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/problems")
public class ProblemApiController {

    private final ProblemMapper problemMapper;
    private final ProblemService problemService;

    @GetMapping("")
    public ResponseEntity<?> getProblemList(@RequestParam(value = "step", defaultValue = "0") int step,
                                            @RequestParam(value = "tag", defaultValue = "") String tagName){
        List<ProblemOnlyDto> problemList = problemService.getProblemListByStepOrTag(step, tagName).stream()
                                                                                    .map(problemMapper::toReviewExcludeDto)
                                                                                    .collect(Collectors.toList());
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, SUCCESS_GET_PROBLEM_LIST, problemList)
        );
    }

    @GetMapping("/{problemId}")
    public ResponseEntity<?> getProblem(@PathVariable Long problemId) {
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, SUCCESS_GET_PROBLEM, problemMapper.toDto(problemService.getProblemById(problemId)))
        );
    }

    @AuthCheck
    @PostMapping("")
    public ResponseEntity<?> saveProblem(@RequestBody ProblemRequestDto requestDto) {
        Member member = MemberContext.currentMember.get();
        problemService.registerProblem(problemMapper.toEntity(requestDto, member), requestDto);

        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, SuccessMessage.SUCCESS_REGISTER_PROBLEM));
    }

    @AuthCheck
    @PutMapping("/{problemId}/step")
    public ResponseEntity<?> updateStep(@PathVariable Long problemId, @RequestBody ProblemStepUpdateDto updateDto) {
        Member member = MemberContext.currentMember.get();
        problemService.updateStep(problemId, member, updateDto.getStep());

        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, SUCCESS_UPDATE_PROBLEM));
    }

    @DeleteMapping("/{problemId}")
    public ResponseEntity<?> deleteProblem(@PathVariable Long problemId) {
        Member member = MemberContext.currentMember.get();
        problemService.deleteProblem(problemId, member);

        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, SUCCESS_DELETE_PROBLEM));
    }

}
