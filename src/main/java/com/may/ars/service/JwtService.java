package com.may.ars.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.may.ars.common.advice.exception.JsonWriteException;
import com.may.ars.dto.member.JwtPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${ars.secret_key}")
    private String SECRET_KEY;

    public String createToken(JwtPayload payload) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + 1000 * 60 * 30 * 60); // 30m * 60
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);

        Map<String, Object> headerMap = new HashMap<>();

        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        try {
            return Jwts.builder()
                    .setHeader(headerMap)
                    .setSubject(objectMapper.writeValueAsString(payload))
                    .setExpiration(expireTime)
                    .signWith(signingKey, signatureAlgorithm)
                    .compact();
        } catch (JsonProcessingException e) {
            throw new JsonWriteException();
        }
    }

    public JwtPayload getPayload(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return objectMapper.readValue(claims.getSubject(), JwtPayload.class);
        } catch (JsonProcessingException | IllegalArgumentException | MalformedJwtException e) {
            throw new JsonWriteException();
        }
    }
}
