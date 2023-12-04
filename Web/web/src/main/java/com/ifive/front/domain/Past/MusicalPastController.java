package com.ifive.front.domain.Past;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/past")
public class MusicalPastController {
    
    // tagName 맵을 클래스 변수로 선언
    private static final Map<String, String> tagName = new HashMap<>();
    private static final Map<String, String> tagNameFront = new HashMap<>();
    // 클래스 변수를 정적 블록에서 초기화
    static {
        tagName.put("love", "사랑");
        tagName.put("opera", "오페라");
        tagName.put("child", "아이");
        tagName.put("friend", "친구");
        tagName.put("art", "예술");
        tagName.put("magic", "마법");
        tagName.put("person", "사람");
        tagName.put("history", "역사");
        tagName.put("fear", "공포");
        tagName.put("laugh", "웃음");
        tagName.put("sad", "슬픔");

        tagNameFront.put("love", "사랑");
        tagNameFront.put("opera", "오페라");
        tagNameFront.put("child", "가족");
        tagNameFront.put("friend", "친구");
        tagNameFront.put("art", "예술");
        tagNameFront.put("magic", "판타지");
        tagNameFront.put("person", "휴머니즘");
        tagNameFront.put("history", "역사");
        tagNameFront.put("fear", "공포");
        tagNameFront.put("laugh", "코미디");
        tagNameFront.put("sad", "슬픔");
    }

    @Autowired
    private MusicalPastService musicalPastService;
 
    
    public MusicalPastController(MusicalPastService musicalPastService){
        this.musicalPastService = musicalPastService;
    }

    @GetMapping("/tags/tag1")
    public String sendTag1(@RequestParam(name = "tag1") String tag1, Model model){
        
        List<MusicalPastDTO> mpdl = musicalPastService.getMusicalPastListByTag1(tagName.get(tag1));
        
        if(mpdl.size() == 0){
            return "domain/Tag/null_tag" ;
        }
        else{
            log.info("dwdw : "+ tagName.keySet());
            log.info("dwdw : "+ tagName.values());
            
            model.addAttribute("musicals", mpdl);
            model.addAttribute("tag1", tag1);
            model.addAttribute("tagName1", tagNameFront.get(tag1));
            model.addAttribute("tagList", tagName.keySet());
            model.addAttribute("tagNameList", tagName.values());
            model.addAttribute("tag2", null);
            model.addAttribute("tagName2", null);
        
        return "domain/Tag/tag_list" ;
        }
    }

    @GetMapping("/tags/tag1&tag2")
    public String sendTag2(@RequestParam(name = "tag1") String tag1, 
    @RequestParam(name = "tag2") String tag2, Model model){
        
        List<MusicalPastDTO> mpdl = musicalPastService.getMusicalPastListByTag1AndTag2(tagName.get(tag1), tagName.get(tag2));

        if(mpdl.size() == 0){
            return "domain/Tag/null_tag" ;
        }
        else{
        
            model.addAttribute("musicals", mpdl);
            model.addAttribute("tag1", tag1);
            model.addAttribute("tag2", tag2);
            model.addAttribute("tagName1", tagNameFront.get(tag1));
            model.addAttribute("tagName2", tagNameFront.get(tag2));

            return "domain/Tag/tag_list";
        }
        
    }

    @GetMapping("/tags/allTagsSelected")
    public String sendAllTags(@RequestParam(name = "tag1") String tag1, 
    @RequestParam(name = "tag2") String tag2, @RequestParam(name = "tag3") String tag3, Model model){
    
        List<MusicalPastDTO> mpdl = musicalPastService.getMusicalPastListByAllTags(
                                            tagName.get(tag1),tagName.get(tag2), tagName.get(tag3)
                                        );

        if(mpdl.size() == 0){
            return "domain/Tag/null_tag" ;
        }
        else{

            model.addAttribute("musicals", mpdl);
            model.addAttribute("tagName1", tagNameFront.get(tag1));
            model.addAttribute("tagName2", tagNameFront.get(tag2));
            model.addAttribute("tagName3", tagNameFront.get(tag3));

            return "domain/Tag/all_tags_list" ;
        }
    }
}
