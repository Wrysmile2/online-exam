package com.xyh.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Slf4j
public class JwtUtil {

    public static final Long JWT_TTL = 24 * 60 * 60 *1000L;

    public static final String JWT_KEY = "xyh";

    /**
     *生成秘钥
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if(ttlMillis==null){
            ttlMillis=JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)              //唯一的ID
                .setSubject(subject)   // 主题  可以是JSON数据
                .setIssuer("xyh")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }

    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, Utils.getUUID());// 设置过期时间
        return builder.compact();
    }
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);// 设置过期时间
        return builder.compact();
    }
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, Utils.getUUID());// 设置过期时间
        return builder.compact();
    }

//    public static void main(String[] args) throws Exception {
////        String jwt = createJWT("123456");
//
//        Claims claims = parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0NjYwZjg2OGRiMDE0NDI3YTI5NDk2YTlmM2E1NTM0OCIsInN1YiI6IjEyMzQ1NiIsImlzcyI6Inh5aCIsImlhdCI6MTY3NzU4OTU4NiwiZXhwIjoxNjc3NTkxMzg2fQ.zT5kSDpCVWV8wizyq7wcuNdzu_Xztb01U4cmWci7rzs");
//        String subject = claims.getSubject();
//        System.out.println(subject);
//    }
}
