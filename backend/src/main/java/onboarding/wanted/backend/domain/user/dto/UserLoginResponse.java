package onboarding.wanted.backend.domain.user.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserLoginResponse {

    private String accessToken;
    private String refreshToken;
}