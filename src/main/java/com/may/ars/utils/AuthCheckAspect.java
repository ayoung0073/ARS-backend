package com.may.ars.utils;

import com.may.ars.dto.JwtPayload;
import com.may.ars.model.entity.member.MemberRepository;
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

@Slf4j
@Aspect
@RequiredArgsConstructor
@Component
public class AuthCheckAspect {

    private static final String AUTHORIZATION = "Authorization";

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final HttpServletRequest httpServletRequest;

    @Around("@annotation(AuthCheck)")
    public void loginCheck(ProceedingJoinPoint pjp) throws Throwable {

        String token = httpServletRequest.getHeader(AUTHORIZATION);

        log.info("AuthCheck : " + token);
        JwtPayload payload = jwtService.getPayload(token);
        log.info("AuthCheck(email) : " + payload.getEmail());

        if(memberRepository.findByEmail(payload.getEmail()).isEmpty()) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "인증 실패") {};
        }
        log.info(String.valueOf(pjp.getArgs().length));
        for(Object obj : pjp.getArgs()) log.info(obj.getClass().getName());
        pjp.proceed(new Object[]{pjp.getArgs()[0], memberRepository.findByEmail(payload.getEmail()).get()});
    }
}
