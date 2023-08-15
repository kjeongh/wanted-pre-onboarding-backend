package onboarding.wanted.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //AUTH
    INVALID_JWT_SIGN(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 refresh 토큰입니다."),

    //USER
    LOGIN_USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "로그인 유저 정보가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
