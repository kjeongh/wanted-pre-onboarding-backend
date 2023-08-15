package onboarding.wanted.backend.global.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ResponseBody {

    private String message;
    private Object data;

    public static ResponseBody of (ResultCode resultCode, Object data) {
        return new ResponseBody(resultCode.getMessage(), data);
    }

}