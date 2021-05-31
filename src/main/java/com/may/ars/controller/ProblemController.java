package com.may.ars.controller;

import com.may.ars.model.entity.problem.Problem;
import com.may.ars.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("/register")
    public String registerPage() {
        return "/algorithm/register";
    }

//    @GetMapping("/detail/{problemId}")
//    public String detailPage(@PathVariable Long problemId, Model model) {
//        Problem problem = problemService.getProblemById(problemId);
//        model.addAttribute("problem", problem);
//        model.addAttribute("review", problem.getReviewList().get(0));
//        model.addAttribute("createdDate", problem.getReviewList().get(0).getCreatedDate());
//
//        return "/algorithm/detail";
//    }

    @GetMapping("/detail/{problemId}")
    public String detailPage(@PathVariable Long problemId, @RequestParam("index") int index, Model model) {
        Problem problem = problemService.getProblemById(problemId);
        model.addAttribute("problem", problem);
        model.addAttribute("review", problem.getReviewList().get(index - 1));
        model.addAttribute("createdDate", problem.getReviewList().get(0).getCreatedDate());

        return "/algorithm/detail";
    }

}
