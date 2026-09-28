package com.workboard.workboard.notice;

import com.workboard.workboard.notice.dto.NoticeDetailRequest;
import com.workboard.workboard.notice.dto.NoticeDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 1. 게시글 등록 (POST)
    @PostMapping
    public ResponseEntity<NoticeDetailResponse> createNotice(
            @RequestBody NoticeDetailRequest request,
            @RequestParam Long memberId) {
        NoticeDetailResponse response = noticeService.create(request, memberId);
        return ResponseEntity.ok(response);
    }

    // 2. 게시글 전체 목록 조회 (GET)
    @GetMapping
    public ResponseEntity<List<NoticeDetailResponse>> getAllNotices() {
        List<NoticeDetailResponse> notices = noticeService.findAll();
        return ResponseEntity.ok(notices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeDetailResponse> getNotice(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.findById(id));
    } //상세보기
}