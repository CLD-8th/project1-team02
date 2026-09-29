package com.workboard.workboard.auth;

import com.workboard.workboard.auth.dto.LoginRequest;
import com.workboard.workboard.auth.dto.ReissueRequest;
import com.workboard.workboard.auth.dto.TokenResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 인증 요청 수신.
 *
 * 주소에 동사를 두지 않는 원칙의 예외.
 * 관례가 널리 자리 잡아 자원으로 표현하면 오히려 낯설어짐.
 */
@Tag(name = "인증", description = "로그인 · 재발급 · 로그아웃")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestBody ReissueRequest request) {
        String access = authService.reissue(request.refreshToken());
        return ResponseEntity.ok(new TokenResponse(access, null, null, null));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request.email(), request.password()));
    }

    /**
     * 로그아웃.
     *
     * 갱신 토큰을 제거하고 접근 토큰을 차단.
     * 인증이 필요하므로 신원과 토큰을 함께 확보.
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal Long memberId,
                                       @RequestHeader("Authorization") String header) {

        authService.logout(memberId, header.substring(7));
        return ResponseEntity.noContent().build();
    }
}
