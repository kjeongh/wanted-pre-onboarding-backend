package onboarding.wanted.backend.domain.auth.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.auth.repository.RefreshTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    //TODO: 토큰 만료시간
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 60;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 60;//TODO: 테스트용으로 시간 짧게 설정, 바꿔야 함

    @Value("${jwt.secret}")
    private String secret; //TODO: autowire

    private Key key;
    private final RefreshTokenRepository refreshTokenRepository;

    //HMAC key설정
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 권한 객체로 access token 생성
    public String createAccessToken(Authentication authentication) {
        String authorities = getAuthString(authentication);

        long now = (new Date()).getTime();
        Date expiration = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expiration)
                .compact();

        return accessToken;
    }

    // 권한 객체로 refresh token 생성
    public String createRefreshToken(Authentication authentication) {
        String authorities = getAuthString(authentication);

        long now = (new Date()).getTime();
        Date expiration = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String refreshToken =  Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expiration)
                .compact();

        //TODO: Redis에 토큰 저장

        return refreshToken;

    }

    // 권한객체로 authorities 문자열 생성
    private String getAuthString (Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    // token의 정보로부터 Authentication 객체 리턴
    public Authentication getAuthentication(String token) {

        //토큰으로부터 claim 파싱
        Claims claims = parseClaims(token);

        //토큰의 "auth"클레임으로부터 권한 객체 생성
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // 권한 정보를 담은 User객체 생성
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // token string으로부터 claim 파싱
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();

        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }


    // 토큰의 유효성 검증, 토큰을 파싱하여 exception들을 캐치
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}