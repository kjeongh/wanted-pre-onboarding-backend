package onboarding.wanted.backend.domain.user.service;

import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.user.User;
import onboarding.wanted.backend.domain.user.dto.UserSignupRequest;
import onboarding.wanted.backend.domain.user.dto.UserSignupResponse;
import onboarding.wanted.backend.domain.user.mapper.UserMapper;
import onboarding.wanted.backend.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserSignupResponse signup(UserSignupRequest signupReqDto) {
        if(userRepository.existsByEmail(signupReqDto.getEmail())) {
//            throw new DuplicateUserException("이미 가입되어 있는 유저입니다.");
            System.out.println("이미 가입한 유저");
        } //TODO: 비활성화 유저는 비활성화 유저 체크

        User user = userMapper.toEntity(signupReqDto);
        userRepository.save(user);

        return UserSignupResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();

    }}