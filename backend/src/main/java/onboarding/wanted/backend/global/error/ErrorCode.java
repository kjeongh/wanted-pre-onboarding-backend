package onboarding.wanted.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // AUTH
    INVALID_JWT_SIGN(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 refresh 토큰입니다."),

    // USER
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 계정입니다."),
    LOGIN_USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "로그인 유저 정보가 존재하지 않습니다."),

    // POST
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    POST_UPDATE_FORBIDDEN(HttpStatus.FORBIDDEN, "게시글 수정 자격이 없습니다.");

    private final HttpStatus status;
    private final String message;

}
