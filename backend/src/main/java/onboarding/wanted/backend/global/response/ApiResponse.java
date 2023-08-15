package onboarding.wanted.backend.global.response;

import org.springframework.http.ResponseEntity;

public class ApiResponse {

    public static ResponseEntity<ResponseBody> of (ResultCode resultCode) {
        return ResponseEntity
                .status(resultCode.getStatus())
                .body(ResponseBody.of(resultCode, ""));
    }

    public static ResponseEntity<ResponseBody> of (ResultCode resultCode, Object data) {
        return ResponseEntity
                .status(resultCode.getStatus())
                .body(ResponseBody.of(resultCode, data));
    }
}
