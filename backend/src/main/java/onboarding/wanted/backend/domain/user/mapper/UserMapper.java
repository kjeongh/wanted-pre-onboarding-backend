package onboarding.wanted.backend.domain.user.mapper;

import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.user.User;
import onboarding.wanted.backend.domain.user.dto.UserSignupRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    public User toEntity(UserSignupRequest signupReqDto) {
        return User.builder()
                .email(signupReqDto.getEmail())
                .username(signupReqDto.getUsername())
                .password(passwordEncoder.encode(signupReqDto.getPassword()))
                .build();
    }
}
