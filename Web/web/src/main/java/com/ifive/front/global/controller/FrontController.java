package com.ifive.front.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

// 뷰와 연결하는 컨트롤러
@Slf4j
@Controller
public class FrontController {
    
    @GetMapping("/")
    public String drawHome(Model model, HttpSession session) {
        log.info("log message : {} start", "home(index.html)");
        return "global/index";
    }

    // 처음 모든 태그 리스트 값 출력된 페이지 이동
    @GetMapping("/tags")
    public String tagList() {
        return "domain/Tag/init_tag";
    }

}