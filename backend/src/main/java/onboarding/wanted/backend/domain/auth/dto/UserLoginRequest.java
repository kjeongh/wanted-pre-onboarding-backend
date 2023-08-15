package onboarding.wanted.backend.domain.auth.dto;

import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserLoginRequest {

    @Pattern(regexp = ".*@.*", message = "올바른 이메일 형식을 입력하세요. '@'가 포함되어야 합니다. 예)gildong@gmail.com")
    private String email;

    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

}
