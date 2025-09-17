package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {

    @GetMapping("/error")
    public String error() {
        throw new IllegalArgumentException("에러를 남깁니다.");
    }

    @GetMapping("/normal")
    public String normal() {
        return "normal";
    }


}
