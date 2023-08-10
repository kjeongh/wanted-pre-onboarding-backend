package onboarding.wanted.backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import onboarding.wanted.backend.domain.user.auth.Role;
import onboarding.wanted.backend.global.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = ".*@.*", message = "올바른 이메일 형식을 입력하세요. 예)gildong@gmail.com")
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    //TODO: 비밀번호 8자 이상
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
}
