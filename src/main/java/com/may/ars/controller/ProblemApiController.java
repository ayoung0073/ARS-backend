package com.may.ars.controller;

import com.may.ars.domain.member.Member;
import com.may.ars.dto.common.ResponseDto;
import com.may.ars.dto.problem.request.ProblemNotificationUpdateDto;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.may.ars.common.message.SuccessMessage.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/problems")
public class ProblemApiController {

    private final ProblemService problemService;

    private final ProblemMapper problemMapper;

    @GetMapping
    public ResponseEntity<?> getProblemList(@RequestParam(value = "step", defaultValue = "0") int step,
                                            @RequestParam(value = "tag", defaultValue = "") String tagName,
                                            @RequestParam(value = "cursorId", defaultValue = "0") Long cursorId,
                                            @RequestParam(value = "size", defaultValue = "12") int size){
        // PageRequest.of()의 첫 번째 파라미터는 무조건 0으로, 즉 최초의 페이지로 처리를 해야 한다.
        List<ProblemOnlyDto> problemList = problemService.getProblemListByStepOrTag(step, tagName, cursorId, PageRequest.of(0, size));
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

    @GetMapping("/count")
    public ResponseEntity<?> getProblemCount() {
        return ResponseEntity.ok(ResponseDto.of(
                HttpStatus.OK, SUCCESS_GET_PROBLEM_COUNT, problemService.getProblemCount()
        ));
    }

    @AuthCheck
    @PostMapping
    public ResponseEntity<?> saveProblem(@RequestBody @Valid ProblemRequestDto requestDto) {
        Member member = MemberContext.currentMember.get();

        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK,
                SuccessMessage.SUCCESS_REGISTER_PROBLEM,
                problemService.registerProblem(problemMapper.toEntity(requestDto, member), requestDto)
        ));
    }

    @AuthCheck
    @PutMapping("/{problemId}/step")
    public ResponseEntity<?> updateStep(@PathVariable Long problemId, @RequestBody @Valid ProblemStepUpdateDto updateDto) {
        Member member = MemberContext.currentMember.get();
        problemService.updateStep(problemId, member, updateDto.getStep());

        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, SUCCESS_UPDATE_PROBLEM));
    }

    @AuthCheck
    @PutMapping("/{problemId}/notification")
    public ResponseEntity<?> updateNotificationDate(@PathVariable Long problemId, @RequestBody @Valid ProblemNotificationUpdateDto updateDto) {
        Member member = MemberContext.currentMember.get();
        problemService.updateNotificationDate(problemId, member, updateDto.getNotificationDate());

        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, SUCCESS_UPDATE_PROBLEM));
    }

    @DeleteMapping("/{problemId}")
    public ResponseEntity<?> deleteProblem(@PathVariable Long problemId) {
        Member member = MemberContext.currentMember.get();
        problemService.deleteProblem(problemId, member);

        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, SUCCESS_DELETE_PROBLEM));
    }

}
