package com.ifive.front.domain.Rank;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/rank")
public class MusicalRankController {
    
    @Autowired
    private MusicalRankService musicalRankService;

    @Autowired
    public MusicalRankController(MusicalRankService musicalRankService) {
        this.musicalRankService = musicalRankService;
    }  

    // 각 사이트 별로 GetMapping 개설
    @GetMapping("/interpark")
    public String interparkRank(Model model){
        LocalDate today = LocalDate.now();
        log.info("오늘 날짜 : "+today);

        List<MusicalRankDTO> mrd = musicalRankService.getMusicalRankListByUpdateDateSiteName(today.toString()
        ,"인터파크");
        
        model.addAttribute("today", today);
        model.addAttribute("mrd", mrd);

        return "domain/Rank/rank_interpark";
    }
        
    @GetMapping("/ticketlink")
    public String ticketlinkRank(Model model){
        LocalDate today = LocalDate.now();
        log.info("오늘 날짜 : "+today);

        List<MusicalRankDTO> mrd = musicalRankService.getMusicalRankListByUpdateDateSiteName(today.toString()
        ,"티켓링크");

        model.addAttribute("today", today);
        model.addAttribute("mrd", mrd);

        return "domain/Rank/rank_ticketlink";
    }
        
    @GetMapping("/yes24")
    public String yes24ticketRank(Model model){
        LocalDate today = LocalDate.now();
        log.info("오늘 날짜 : "+today);

        List<MusicalRankDTO> mrd = musicalRankService.getMusicalRankListByUpdateDateSiteName(today.toString()
        ,"예스24티켓");
        
        model.addAttribute("today", today);
        model.addAttribute("mrd", mrd);

        return "domain/Rank/rank_yes24ticket";
    }
}
