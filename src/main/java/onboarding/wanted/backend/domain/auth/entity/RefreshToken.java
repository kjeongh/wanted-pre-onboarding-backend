package onboarding.wanted.backend.domain.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 60) // TODO: 테스트를 위해 짧은 시간 설정
public class RefreshToken {

    @Id
    @Getter
    private String refreshToken;

    // 이후 redis에 저장되는 정보 추가 예정
}
