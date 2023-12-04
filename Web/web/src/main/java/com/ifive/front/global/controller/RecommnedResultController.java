package com.ifive.front.global.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifive.front.domain.Future.MusicalFutureDTO;
import com.ifive.front.domain.Future.MusicalFutureService;
import com.ifive.front.domain.Present.MusicalPresentDTO;
import com.ifive.front.domain.Present.MusicalPresentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/result")
public class RecommnedResultController {
    @Autowired
    private MusicalPresentService musicalPresentService;
    @Autowired
    private MusicalFutureService musicalFutureService;

    @Autowired
    public RecommnedResultController(MusicalPresentService musicalPresentService
                                    ,MusicalFutureService musicalFutureService)
    {
        this.musicalPresentService=musicalPresentService;
        this.musicalFutureService=musicalFutureService;
    }

    @PostMapping("/ml")
    public String getRecommends(@RequestParam(name = "id") String id
                                ,@RequestParam(name = "title") String title
                                ,RedirectAttributes redirectAttributes)
    {
        List<MusicalPresentDTO> musicalPresents = musicalPresentService.getPresentDTOsbyIdFromML(id);

        List<MusicalFutureDTO> musicalFutures = musicalFutureService.getFutureDTOsbyIdFromML(id);
        
        // log.info("현재 뮤지컬 추천 : "+musicalPresents);
        // log.info("과거 뮤지컬 중 선택한 뮤지컬 이름 : "+title);
        // log.info("미래 뮤지컬 추천 : "+musicalFutures);

        redirectAttributes.addFlashAttribute("musicalPresents", musicalPresents);
        redirectAttributes.addFlashAttribute("musicalFutures", musicalFutures);
        redirectAttributes.addFlashAttribute("title", title);

        return "redirect:/result/recommends";
    }

    @GetMapping("/recommends")
    public String showRecommends(@ModelAttribute("musicalPresents") List<MusicalPresentDTO> presentDTOs
                                ,@ModelAttribute("musicalFutures") List<MusicalFutureDTO> futureDTOs
                                ,@ModelAttribute("title") String title
                                ,Model model)
    {
        // presentDTOs와 futureDTOs를 합친 리스트
        List<Object> combinedDTOs = new ArrayList<>();
        combinedDTOs.addAll(presentDTOs);
        combinedDTOs.addAll(futureDTOs);

        // 모델에 합쳐진 리스트 추가
        model.addAttribute("musicals", combinedDTOs);

        model.addAttribute("title", title);
        return "global/recommendResult";
    }
}
