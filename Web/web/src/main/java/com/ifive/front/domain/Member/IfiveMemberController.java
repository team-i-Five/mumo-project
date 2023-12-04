package com.ifive.front.domain.Member;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.dao.DataIntegrityViolationException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class IfiveMemberController {

    private final IfiveMemberServiceImpl memberService;

    // Get으로 signup url이 요청되면 회원가입 렌더링
    @GetMapping("/signin")
    public String signin() {
        return "domain/Member/signin_form";
    }

    // Get으로 signup url이 요청되면 회원가입폼 렌더링
    @GetMapping("/signup") 
    public String signup(MemberCreateForm memberCreateForm) {
        return "domain/Member/signup_form";
    }

    // Post로 요청시 회원가입 진행 , @Valid는 유효성 검사를 위해서 사용함.
    @PostMapping("/signup") 
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "domain/Member/signup_form";
        }
        // bindingResult.rejectValue(필드명, 오류코드, 에러메시지)
        if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "패스워드가 일치하지 않습니다.");
            return "domain/Member/signup_form";
        }

        // 회원가입시 오류 체크 어노테이션에 체크한 unique=true값으로 중복이 체크되면, DataIntegrityViolationException발생
        // 기타 에러는 Exception 메세지 처리
        try {
            memberService.create(
                    memberCreateForm.getMemberName(), memberCreateForm.getEmail(), memberCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 멤버.");
            return "domain/Member/sigunup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "domain/Member/sigunup_form";
        }
        return "redirect:/";
    }
}