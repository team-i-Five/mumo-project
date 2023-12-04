package com.ifive.front.global.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifive.front.domain.Past.MusicalPastDTO;
import com.ifive.front.domain.Past.MusicalPastServiceImpl;

import lombok.extern.slf4j.Slf4j;

// 뷰와 연결하는 컨트롤러
@Slf4j
@RequestMapping("/search")
@Controller
public class SearchController {
    @Autowired
    private MusicalPastServiceImpl musicalPastServiceImpl;

    
    public SearchController(MusicalPastServiceImpl musicalPastServiceImpl){
        this.musicalPastServiceImpl = musicalPastServiceImpl;
    }

    // 검색
    @GetMapping("/")
    public String search(Model model) {
        List<MusicalPastDTO> mpdl = musicalPastServiceImpl.getMusicalPastListOrderByEndDate(25);

        model.addAttribute("musicals", mpdl);
        return "domain/Search/search";
    }

    // 검색 결과
    @PostMapping("/result")
    public String searchResult(Model model, @RequestParam("searchKeyword") String searchKeyword) {
        // log.info("***** resultSearchKeyword={}", searchKeyword);
        List<MusicalPastDTO> mpdl = musicalPastServiceImpl.getMusicalPastListBySearch(searchKeyword);

        // 검색 결과가 없을 때 null 페이지로, 하나라도 있으면 search 페이지로 리턴
        if (mpdl.size()==0){
            return "domain/Search/null_search";
        }
        else{
            model.addAttribute("musicals", mpdl);
            model.addAttribute("searchKeyword", searchKeyword);
            return "domain/Search/search";
        }
    }
}