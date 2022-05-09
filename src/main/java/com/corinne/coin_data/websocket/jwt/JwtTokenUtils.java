package com.corinne.coin_data.websocket.jwt;

import com.auth0.jwt.algorithms.Algorithm;

public final class JwtTokenUtils {

    private static final int SEC = 1;
    private static final int MINUTE = 60 * SEC;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    private static final int JWT_TOKEN_VALID_SEC = 3 * DAY;

    private static final int JWT_TOKEN_VALID_MILLI_SEC = JWT_TOKEN_VALID_SEC * 1000;

    public static final String CLAIM_EXPIRED_DATE = "EXPIRED_DATE";
    public static final String CLAIM_USER_EMAIL = "USER_EMAIL";
    public static final String CLAIM_USER_NICKNAME = "CLAIM_USER_NICKNAME";
    public static final String JWT_SECRET = "jwt_secret_!@#$%";

    private static Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(JWT_SECRET);
    }
}
