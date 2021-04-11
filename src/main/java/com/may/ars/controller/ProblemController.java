package com.may.ars.controller;

import com.may.ars.dto.problem.ProblemRegisterDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
public class ProblemController {

    @GetMapping("/register")
    public String registerPage() {
        return "/algorithm/register";
    }

    @PostMapping("/api/problem")
    public String saveProblem(@RequestBody ProblemRegisterDto registerDto) {
        log.info("글 등록");
        log.info(registerDto.toString());

        return "redirect:/";
    }
}
