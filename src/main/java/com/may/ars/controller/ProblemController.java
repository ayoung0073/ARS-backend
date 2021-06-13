package com.may.ars.controller;

import com.may.ars.domain.problem.Problem;
import com.may.ars.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("")
    public String registerPage() {
        return "/algorithm/register";
    }

    @GetMapping("/{problemId}")
    public String detailPage(@PathVariable Long problemId, @RequestParam("index") int index, Model model) {
        Problem problem = problemService.getProblemById(problemId);
        log.info(problem.toString());
        model.addAttribute("problem", problem);
        model.addAttribute("index", index);
        model.addAttribute("review", problem.getReviewList().get(index - 1));
        model.addAttribute("createdDate", problem.getReviewList().get(0).getCreatedDate());

        return "/algorithm/detail";
    }

    @GetMapping("/{problemId}/reviews")
    public String registerReviewPage(@PathVariable Long problemId, Model model) {
        log.info(problemId + "");
        Problem problem = problemService.getProblemById(problemId);
        model.addAttribute("problem", problem);
        return "/algorithm/reviewRegister";
    }


}
