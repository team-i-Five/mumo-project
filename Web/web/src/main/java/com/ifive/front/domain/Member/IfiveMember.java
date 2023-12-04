package com.ifive.front.domain.Member;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class IfiveMember {  // 스프링 시큐리티에 User가있어서 참조 문제가 생길 수 있음.
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // 중복방지
    private String memberName;
    
    @Column(unique = true)
    private String email;

    private String password;

}
