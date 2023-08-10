package onboarding.wanted.backend.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResultCode {

    //인증, 인가
    USER_SIGNUP_SUCCESS(HttpStatus.CREATED.value(), "회원가입 성공"),
    USER_LOGIN_SUCCESS(HttpStatus.OK.value(), "로그인 성공"),

    //게시글
    POST_CREATE_SUCCESS(HttpStatus.CREATED.value(), "게시글 생성 성공"),
    POST_LIST_GET_SUCCESS(HttpStatus.OK.value(), "게시글 목록 조회 성공"),
    POST_GET_SUCCESS(HttpStatus.OK.value(), "단일 게시글 조회 성공"),
    POST_UPDATE_SUCCESS(HttpStatus.OK.value(), "게시글 수정 성공"),
    POST_DELETE_SUCCESS(HttpStatus.OK.value(), "게시글 삭제 성공");

    private Integer status;
    private String message;


}
