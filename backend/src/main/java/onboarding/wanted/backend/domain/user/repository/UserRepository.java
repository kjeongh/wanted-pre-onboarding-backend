package onboarding.wanted.backend.domain.user.repository;

import onboarding.wanted.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Optional<User>
    Optional<User> findByEmail(@Param("email")String email);
    boolean existsByEmail(String email);
//    @EntityGraph(attributePaths = "authorities")	// Eager(즉시)조회로 authorites 정보를 같이 가져온다.
//    Optional<User> findOneWithAuthoritiesByUsername(String username);
//
}