package com.may.ars.utils;

import com.may.ars.dto.member.MemberDto;

public class MemberContext {
    public static ThreadLocal<MemberDto> currentMember = new ThreadLocal<>();
}