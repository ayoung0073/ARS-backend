package com.may.ars.utils;

import com.may.ars.domain.member.Member;

public class MemberContext {
    public static ThreadLocal<Member> currentMember = new ThreadLocal<>();
}