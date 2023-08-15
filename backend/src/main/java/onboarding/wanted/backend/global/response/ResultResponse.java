package onboarding.wanted.backend.global.response;

import lombok.Getter;

@Getter
public class ResultResponse {

    private final Integer status;
    private final String message;
    private final Object data;

    public ResultResponse(ResultCode result, Object data) {
        this.status = result.getStatus();
        this.message = result.getMessage();
        this.data = data;
    }

    // 응답 Body 없을 경우
    public static ResultResponse of(ResultCode result) {
        return new ResultResponse(result, "");
    }

    // 응답 body 존재할 경우
    public static ResultResponse of(ResultCode result, Object data) {
        return new ResultResponse(result, data);
    }

}