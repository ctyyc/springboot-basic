package com.example.mkapp.biz.service;

import com.example.mkapp.biz.repository.MemoryMemberRepository;
import com.example.mkapp.common.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@SpringBootTest   // 스프링 컨테이너와 테스트를 함께 실행
//@Transactional    // 테스트 후 롤백
class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
//        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    public void join() throws Exception {
        //Given
        Member member = new Member();
        member.setName("hello");
        //When
        Member saveDto = memberService.join(member);
        //Then
        Member findMember = memberRepository.findById(saveDto.getId()).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    public void validateDuplicateMember() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");
        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
//        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        assertEquals(e.getMessage(), "이미 존재하는 회원입니다.");
    }
}