package onboarding.wanted.backend.domain.auth.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken", timeToLive = 60)

public class RefreshToken {

    @Id
    private String refreshToken;
    private String email;

    public RefreshToken(final String refreshToken, final String email) {
        this.refreshToken = refreshToken;
        this.email = email;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getUserEmail() {
        return email;
    }
}
