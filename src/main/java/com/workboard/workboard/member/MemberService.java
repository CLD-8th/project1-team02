package com.workboard.workboard.member;

import com.workboard.workboard.member.dto.MemberRequest;
import com.workboard.workboard.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    // NoticeService 등에서 Entity 객체를 직접 조회할 때 사용
    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + id));
    }

    // 회원 등록 (회원가입 기능)
    @Transactional
    public MemberResponse createMember(MemberRequest request) {
        if (memberRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Member member = new Member(
                request.email(),
                request.password(),
                request.nickname(),
                request.role()
        );

        Member savedMember = memberRepository.save(member);
        return MemberResponse.of(savedMember);
    }

    // 회원 단건 조회 (응답 DTO 반환)
    public MemberResponse getMemberResponse(Long id) {
        Member member = getMember(id);
        return MemberResponse.of(member);
    }
}