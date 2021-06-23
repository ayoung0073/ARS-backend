package com.may.ars.utils.auth;

import com.may.ars.dto.JwtPayload;
import com.may.ars.domain.member.Member;
import com.may.ars.domain.member.MemberRepository;
import com.may.ars.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@Aspect
@RequiredArgsConstructor
@Component
public class AuthCheckAspect {

    private static final String AUTHORIZATION = "Authorization";

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final HttpServletRequest httpServletRequest;

    @Around("@annotation(com.may.ars.utils.auth.AuthCheck)")
    public Object loginCheck(ProceedingJoinPoint pjp) throws Throwable {

        String token = httpServletRequest.getHeader(AUTHORIZATION);

        log.info("AuthCheck : " + token);
        JwtPayload payload = jwtService.getPayload(token);
        log.info("AuthCheck(email) : " + payload.getEmail());

        Optional<Member> optionalMember = memberRepository.findByEmail(payload.getEmail());
        if(optionalMember.isEmpty()) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "인증 실패") {};
        }

        MemberContext.currentMember.set(optionalMember.get());
        return pjp.proceed();
    }
}
