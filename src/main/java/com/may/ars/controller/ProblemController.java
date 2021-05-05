package com.may.ars.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ProblemController {

    @GetMapping("/register")
    public String registerPage() {
        return "/algorithm/register";
    }

}
