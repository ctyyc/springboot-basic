package com.example.mkapp.biz.aspect;

import com.example.mkapp.biz.service.MemberService;
import com.example.mkapp.common.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Aspect
@Slf4j
public class MemberAspect {

    private final MemberService memberService;

    @Async
    @AfterReturning(pointcut = "execution(* com.example.mkapp.biz.service.MemberService.join(..))", returning = "retVal")
    public void afterSaveMember(Object retVal) throws Throwable {
        Member member = (Member) retVal;

        log.info("### Member Name : {}", member.getName());
    }
}
