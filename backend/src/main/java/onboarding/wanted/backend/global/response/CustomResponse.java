package onboarding.wanted.backend.global.response;

import lombok.Getter;

@Getter
public class CustomResponse {

    private final Integer status;
    private final String message;
    private final Object data;

    public CustomResponse(ResultCode result, Object data) {
        this.status = result.getStatus();
        this.message = result.getMessage();
        this.data = data;
    }

    public static CustomResponse of(ResultCode result, Object data) {
        return new CustomResponse(result, data);
    }

}