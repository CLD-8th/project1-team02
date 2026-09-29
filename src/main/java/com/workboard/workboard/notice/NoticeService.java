package com.workboard.workboard.notice;

import com.workboard.workboard.common.NotFoundException; //상세보기
import com.workboard.workboard.member.Member;
import com.workboard.workboard.member.MemberService;
import com.workboard.workboard.notice.dto.NoticeDetailRequest;
import com.workboard.workboard.notice.dto.NoticeDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본은 readOnly
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberService memberService;

    // 1. 새 글 생성 시 목록 캐시 삭제 (데이터 불일치 방지)
    @CacheEvict(value = "notices", allEntries = true)
    @Transactional
    public NoticeDetailResponse create(NoticeDetailRequest request, Long memberId) {
        Member writer = memberService.getMember(memberId);

        Notice notice = new Notice(
                request.title(),
                request.content(),
                request.department(),
                writer
        );

        Notice savedNotice = noticeRepository.save(notice);
        return NoticeDetailResponse.of(savedNotice);
    }
    // 2. 전체 및 부서별 목록 조회 캐싱
    @Cacheable(value = "notices", key = "#department != null ? #department : 'all'")
    public List<NoticeDetailResponse> findAll(String department) {
        List<Notice> notices;

        if(department != null && !department.isBlank()){
            notices = noticeRepository.findByDepartment(department);
        } else {
            notices = noticeRepository.findAll();
        }

        return notices.stream()
                .map(NoticeDetailResponse::of)
                .collect(Collectors.toCollection(ArrayList::new)); // Java 16 이상 (.collect(Collectors.toList()) 로 작성하셔도 됩니다)
    }
    // 3. 단건 상세 조회 캐싱
    @Cacheable(value = "noticeDetail", key = "#id")
    public NoticeDetailResponse findById(Long id) {
        Notice notice = getWithWriter(id);
        return NoticeDetailResponse.of(notice);
    } //상세보기

    public Notice getWithWriter(Long id) {
        return noticeRepository.findWithWriterById(id)
                .orElseThrow(() -> new NotFoundException("공지 부재"));
    } //상세보기
}