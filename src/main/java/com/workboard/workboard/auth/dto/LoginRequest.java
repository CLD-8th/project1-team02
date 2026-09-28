package com.workboard.workboard.auth.dto;

/**
 * 로그인 요청 형태.
 */
public record LoginRequest(
        String email,
        String password
) {
}
