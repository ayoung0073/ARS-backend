package com.may.ars.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class ProblemController {

    @GetMapping("/register")
    public String registerPage() {
        return "/algorithm/register";
    }

    @PostMapping("/api/problem")
    public String saveProblem() {
        log.info("글 등록");
        return "redirect:/";
    }
}
