package onboarding.wanted.backend.domain.auth.service;

import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.user.entity.User;
import onboarding.wanted.backend.domain.user.repository.UserRepository;
import onboarding.wanted.backend.global.error.ErrorCode;
import onboarding.wanted.backend.global.error.exception.BusinessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BusinessException(ErrorCode.LOGIN_USER_NOT_FOUND));
        return createUserDetails(user);
    }

    private UserDetails createUserDetails(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getUserRole().getRole());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(authority));
    }

}
