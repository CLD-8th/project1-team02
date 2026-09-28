package com.workboard.workboard.member.dto;

import com.workboard.workboard.member.Member;
<<<<<<< HEAD

import java.time.LocalDateTime;

/**
 * 회원 응답 형태.
 *
 * 비밀번호를 담지 않으므로 저장 형태를 그대로 반환할 때의 노출이 부재.
 */
=======
import java.time.LocalDateTime;

>>>>>>> f3ed3c58b04ecf7306624dc96ba2c51ec4c3b369
public record MemberResponse(
        Long id,
        String email,
        String nickname,
<<<<<<< HEAD
        LocalDateTime createdAt
) {
    public static MemberResponse from(Member member) {
=======
        String role,
        LocalDateTime createdAt
) {
    public static MemberResponse of(Member member) {
>>>>>>> f3ed3c58b04ecf7306624dc96ba2c51ec4c3b369
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
<<<<<<< HEAD
                member.getCreatedAt());
    }
}
=======
                member.getRole(),
                member.getCreatedAt()
        );
    }
}
>>>>>>> f3ed3c58b04ecf7306624dc96ba2c51ec4c3b369
