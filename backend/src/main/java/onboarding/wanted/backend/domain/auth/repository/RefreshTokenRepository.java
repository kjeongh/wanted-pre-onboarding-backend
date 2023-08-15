package onboarding.wanted.backend.domain.auth.repository;

import onboarding.wanted.backend.domain.auth.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}