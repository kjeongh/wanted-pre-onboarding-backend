package onboarding.wanted.backend.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import onboarding.wanted.backend.global.error.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {

    private final Integer status;
    private final String message;
    private Object data = null;

    // 응답 데이터 포함한 응답
    public static CustomResponse of(ResultCode result, Object data) {
        return CustomResponse.builder()
                .status(result.getStatus())
                .message(result.getMessage())
                .data(data)
                .build();
    }

    // 응답 데이터가 없는 응답
    public static CustomResponse of(ResultCode result) {
        return CustomResponse.builder()
                .status(result.getStatus())
                .message(result.getMessage())
                .build();
    }

    // 커스텀 에러 응답
    public static CustomResponse of(ErrorCode error) {
        return CustomResponse.builder()
                .status(error.getStatus())
                .message(error.getMessage())
                .build();
    }

    // 에러 응답
    public static CustomResponse of(HttpStatus error) {
        return CustomResponse.builder()
                .status(error.value())
                .message(error.getReasonPhrase())
                .build();
    }

    // 에러 응답
    public static CustomResponse of(HttpStatus error, String message) {
        return CustomResponse.builder()
                .status(error.value())
                .message(message)
                .build();
    }
}

