package onboarding.wanted.backend.global.response;

import onboarding.wanted.backend.global.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {

    // 응답 데이터가 있을 경우
    public static ResponseEntity<CustomResponse> of(ResultCode resultCode, Object data) {
        return ResponseEntity
                .status(resultCode.getStatus())
                .body(CustomResponse.of(resultCode, data));
    }

    // 응답 데이터가 없을 경우
    public static ResponseEntity<CustomResponse> of(ResultCode resultCode) {
        return ResponseEntity
                .status(resultCode.getStatus())
                .body(CustomResponse.of(resultCode));
    }

    // 커스텀 에러 발생
    public static ResponseEntity<CustomResponse> error(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(CustomResponse.of(errorCode));
    }

    // 전역 에러 발생
    public static ResponseEntity<CustomResponse> error(HttpStatus errorCode) {
        return ResponseEntity
                .status(errorCode.value())
                .body(CustomResponse.of(errorCode));
    }

    // 전역 에러 발생
    public static ResponseEntity<CustomResponse> error(HttpStatus errorCode, String message) {
        return ResponseEntity
                .status(errorCode.value())
                .body(CustomResponse.of(errorCode, message));
    }
}
