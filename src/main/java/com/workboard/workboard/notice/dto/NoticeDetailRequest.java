package com.workboard.workboard.notice.dto;

public record NoticeDetailRequest(
        String title,
        String content,
        String department
) {
}
