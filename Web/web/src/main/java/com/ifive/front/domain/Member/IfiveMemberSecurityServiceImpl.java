package com.ifive.front.domain.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;



// 멤버 인증에 관한 서비스를 구현
// 기본적으로 IfiveMemberSecurityService와 스프링 시큐리티 UserDetailService를 implemets
// UserDetailService는 loadUserByUsername메서드를 구현하도록 강제하는 인터페이스
@RequiredArgsConstructor
@Service
public class IfiveMemberSecurityServiceImpl implements UserDetailsService, IfiveMemberSecurityService{
    private final IfiveMemberRepository ifiveMemberRepository;

    // loadUserByUsername : memberName으로 비밀번호를 조회해서 리턴하는 메소드
    // 메소드 설명 : 사용자명(memberName)으로 IfiveMember객체를 조회하고 없다면 UsernameNotFoundException
    // 사용자이름이 admin인경우 ADMIN권한부여, 이외에는 USER 권한부여
    // 그리고 form으로 전달받은 걸, 스프링 시큐리티 User객체를 생성해서 리턴함.
    // 스프링시큐리티는 loadUserByUsername 메소드로 리턴된 User 객체 비밀번호가 form에 입력된 비밀번호와 일치하는지 검색하는
    // 내부로직이있음.
    @Override
    public UserDetails loadUserByUsername(String memberName) throws UsernameNotFoundException {
        Optional<IfiveMember> _ifiveMember = this.ifiveMemberRepository.findBymemberName(memberName);
        if (_ifiveMember.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        IfiveMember ifiveMember = _ifiveMember.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(memberName)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
        }
        return new User(ifiveMember.getMemberName(), ifiveMember.getPassword(), authorities);
    }
}
