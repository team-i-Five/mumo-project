package com.ifive.front.domain.Member;

import org.springframework.security.core.userdetails.UserDetails;


public interface IfiveMemberSecurityService {
    public UserDetails loadUserByUsername(String memberName);
}
