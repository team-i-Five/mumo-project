package com.ifive.front.domain.Member;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IfiveMemberRepository extends JpaRepository<IfiveMember, Long> {
    // 무엇을 기준으로 로그인할지, 여기선 멤버네임을 기준으로한다.
    Optional<IfiveMember> findBymemberName(String memberName);
}
