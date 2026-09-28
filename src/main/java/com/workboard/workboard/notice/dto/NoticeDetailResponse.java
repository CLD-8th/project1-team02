package com.workboard.workboard.notice.dto;

import com.workboard.workboard.notice.Notice;

import java.time.LocalDateTime;

public record NoticeDetailResponse(
        Long id,
        String title,
        String content,
        String department,
        Long writerId,
        String writerName,
        LocalDateTime createdAt
    ) {
    public static NoticeDetailResponse of(Notice notice) {
        return new NoticeDetailResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getDepartment(),
                notice.getWriter().getId(),
                notice.getWriter().getNickname(), // Member 엔티티의 getNickname() 호출
                notice.getCreatedAt()
        );
    }
}
