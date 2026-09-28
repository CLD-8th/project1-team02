package com.workboard.workboard.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 문서의 머리 정보와 인증 방식.
 *
 * 인증 방식을 지정하지 않으면 문서 화면에서 토큰을 넣을 자리가 없어
 * 인증이 필요한 주소를 시험해 볼 수 없음.
 */
@Configuration
public class OpenApiConfig {

    private static final String SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI boardOpenApi() {
        SecurityScheme bearer = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("로그인 응답의 접근 토큰을 입력함. Bearer 는 자동으로 붙음.");

        return new OpenAPI()
                .info(new Info()
                        .title("게시판 API")
                        .description("게시글 · 댓글 · 회원 · 인증")
                        .version("v1"))
                .components(new Components().addSecuritySchemes(SCHEME_NAME, bearer))
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME));
    }
}
