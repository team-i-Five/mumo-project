package com.ifive.front.domain.Member;


import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor // final이나 @NotNull에 대한 생성자 자동생성 enum 또는 class 한정
@Service
public class IfiveMemberServiceImpl {
    private final IfiveMemberRepository ifiveMemberRepository;
    private final PasswordEncoder passwordEncoder;

    public IfiveMember create(String memberName, String email, String password) {
        IfiveMember member = new IfiveMember();
        member.setMemberName(memberName);
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));
        this.ifiveMemberRepository.save(member);
        return member;
    }
}

