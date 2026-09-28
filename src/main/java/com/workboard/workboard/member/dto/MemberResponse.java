package com.workboard.workboard.member.dto;

import com.workboard.workboard.member.Member;
import java.time.LocalDateTime;

public record MemberResponse(
        Long id,
        String email,
        String nickname,
        String role,
        LocalDateTime createdAt
) {
    public static MemberResponse of(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getRole(),
                member.getCreatedAt()
        );
    }
}