package onboarding.wanted.backend.domain.auth.dto;

import lombok.*;
import onboarding.wanted.backend.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSignupResponse {

    private String email;
    private String username;
    private LocalDateTime createdAt;

    public static UserSignupResponse of(User user) {
        return UserSignupResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .build();
    }

}