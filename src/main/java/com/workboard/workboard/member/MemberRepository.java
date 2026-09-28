package com.workboard.workboard.member;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일 중복 확인 및 로그인 조회용
    Optional<Member> findByEmail(String email);
}