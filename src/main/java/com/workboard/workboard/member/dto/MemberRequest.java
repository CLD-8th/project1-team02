package com.workboard.workboard.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

<<<<<<< HEAD
/**
 * 회원 가입 요청 형태.
 */
=======
>>>>>>> f3ed3c58b04ecf7306624dc96ba2c51ec4c3b369
public record MemberRequest(

        @NotBlank(message = "이메일은 필수")
        @Email(message = "이메일 형식이 아님")
        String email,

        @NotBlank(message = "비밀번호는 필수")
        @Size(min = 4, message = "비밀번호는 4자 이상")
        String password,

        @NotBlank(message = "별명은 필수")
        @Size(max = 20, message = "별명은 20자 이하")
<<<<<<< HEAD
        String nickname
) {
=======
        String nickname,

        String role) {
>>>>>>> f3ed3c58b04ecf7306624dc96ba2c51ec4c3b369
}
