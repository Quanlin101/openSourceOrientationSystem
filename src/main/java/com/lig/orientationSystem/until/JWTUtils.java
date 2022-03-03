package com.lig.orientationSystem.until;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lig.orientationSystem.until.error.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
    private static final Logger logger= LoggerFactory.getLogger(JWTUtils.class);

    //密钥
    private static final String SECRET= "violet";
    //过期时间 两个小时
    private static final long EXPIRATION = 7200L;//秒


    //生成token
    public static String createToken(String UserId) {
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        return JWT.create()
                .withHeader(map)
                .withClaim("UserId", UserId)
                .withExpiresAt(expireDate)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(SECRET));

    }
    //token解析
    //非对称加密
    public static Map<String, Claim> validateToken(String token){
        DecodedJWT jwt=null;
        try {
            if (token == null){
                System.out.println("token为空");
            }
            JWTVerifier verifier=JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt=verifier.verify(token);

        }
        catch (TokenExpiredException e){
            logger.error(e.getMessage());
            logger.error("token过期");
//            throw new GlobalException("token过期");
            return null;
        }
        catch (Exception e){
            logger.error(e.getMessage());
            logger.error("token解码异常");
            throw new GlobalException("token解码异常");
        }
        return jwt.getClaims();
    }
}
