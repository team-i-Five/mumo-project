package com.ifive.front.domain.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

// 
@Getter
@Setter
public class MemberCreateForm {
    @Size(min = 3, max = 25)            // 위 항목에 대해서 3~25자라는 길이를 설정합니다.
    @NotEmpty(message = "사용자 ID는 필수로 입력해주세요.")
    private String memberName;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;   // 비밀번호

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;   // 비밀번호확인

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email  // 입력폼이 이메일에 맞는지 검증
    private String email;
}