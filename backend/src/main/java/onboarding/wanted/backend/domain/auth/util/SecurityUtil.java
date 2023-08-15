package onboarding.wanted.backend.domain.auth.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@NoArgsConstructor
public class SecurityUtil {
    public static String getLoginUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }

        return authentication.getName();
    }
}
