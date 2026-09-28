package com.workboard.workboard.member;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD

import java.util.Optional;

/**
 * 회원 저장소.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}
=======
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일 중복 확인 및 로그인 조회용
    Optional<Member> findByEmail(String email);
}
>>>>>>> f3ed3c58b04ecf7306624dc96ba2c51ec4c3b369
