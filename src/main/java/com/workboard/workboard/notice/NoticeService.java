package com.workboard.workboard.notice;

import com.workboard.workboard.common.NotFoundException; //상세보기
import com.workboard.workboard.member.Member;
import com.workboard.workboard.member.MemberService;
import com.workboard.workboard.notice.dto.NoticeDetailRequest;
import com.workboard.workboard.notice.dto.NoticeDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본은 readOnly
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberService memberService;

    @Transactional // <- 생성 메서드에 @Transactional 이 필수입니다!
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

    public List<NoticeDetailResponse> findAll() {
        return noticeRepository.findAll().stream()
                .map(NoticeDetailResponse::of)
                .toList(); // Java 16 이상 (.collect(Collectors.toList()) 로 작성하셔도 됩니다)
    }

    public NoticeDetailResponse findById(Long id) {
        Notice notice = getWithWriter(id);
        return NoticeDetailResponse.of(notice);
    } //상세보기

    public Notice getWithWriter(Long id) {
        return noticeRepository.findWithWriterById(id)
                .orElseThrow(() -> new NotFoundException("공지 부재"));
    } //상세보기
}