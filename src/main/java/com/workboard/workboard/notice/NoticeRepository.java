package com.workboard.workboard.notice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // 부서별 목록 조회를 위한 쿼리 메서드 추가
    List<Notice> findByDepartment(String department);
}
