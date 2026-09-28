package com.workboard.workboard.member;

<<<<<<< HEAD
import com.workboard.workboard.common.NotFoundException;
import com.workboard.workboard.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 회원 업무 규칙.
 */
=======
import com.workboard.workboard.member.dto.MemberRequest;
import com.workboard.workboard.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

>>>>>>> f3ed3c58b04ecf7306624dc96ba2c51ec4c3b369
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
<<<<<<< HEAD
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponse join(String email, String password, String nickname) {
        if (memberRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 가입된 이메일");
        }
        Member saved = memberRepository.save(new Member(email, passwordEncoder.encode(password), nickname));
        return MemberResponse.from(saved);
    }

    /**
     * 정보 수정.
     *
     * 조회한 뒤 값을 바꾸며 저장 호출이 불필요.
     */
    @Transactional
    public MemberResponse update(Long id, String nickname, Long requesterId) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("회원 부재"));

        // 본인 계정만 수정 가능.
        if (!id.equals(requesterId)) {
            throw new SecurityException("권한 부재");
        }

        member.changeNickname(nickname);
        return MemberResponse.from(member);
    }

    /**
     * 탈퇴.
     *
     * 본인 계정만 가능.
     * 작성한 글이 있으면 외래 키 제약으로 삭제가 실패.
     */
    @Transactional
    public void delete(Long id, Long requesterId) {
        if (!id.equals(requesterId)) {
            throw new SecurityException("권한 부재");
        }

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("회원 부재"));
        memberRepository.delete(member);
    }

    public Optional<MemberResponse> findById(Long id) {
        return memberRepository.findById(id).map(MemberResponse::from);
    }
}
=======

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
>>>>>>> f3ed3c58b04ecf7306624dc96ba2c51ec4c3b369
