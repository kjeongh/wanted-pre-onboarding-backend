package onboarding.wanted.backend.domain.user.repository;

import onboarding.wanted.backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
