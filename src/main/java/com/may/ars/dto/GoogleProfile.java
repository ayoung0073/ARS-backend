package com.may.ars.dto;

import lombok.Data;

@Data
public class GoogleProfile {

    private String sub;

    private String name;

    private String given_name;

    private String family_name;

    private String picture; // url

    private String email;

    private boolean email_verified;

    private String locale;
}
