package com.example.mkapp.biz.service;

import com.example.mkapp.biz.repository.JdbcTemplateMemberRepository;
import com.example.mkapp.common.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final JdbcTemplateMemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional(rollbackOn = Throwable.class)
    public Member join(Member member) {
        //중복 회원 검증
        validateDuplicateMember(member);

        memberRepository.save(member);

        return member;
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
