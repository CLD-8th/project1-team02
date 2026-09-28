package com.workboard.workboard.notice;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @EntityGraph(attributePaths = {"writer"})
    Optional<Notice> findWithWriterById(Long id); //상세보기
}
