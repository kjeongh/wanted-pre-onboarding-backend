package onboarding.wanted.backend.global.response;

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
                .body(CustomResponse.of(resultCode, null));
    }
}
