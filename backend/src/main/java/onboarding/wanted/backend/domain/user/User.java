package onboarding.wanted.backend.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import onboarding.wanted.backend.global.BaseEntity;

import static onboarding.wanted.backend.domain.user.UserConstant.EMAIL_REGEXP;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = ".*@.*", message = "올바른 이메일 형식을 입력하세요. 예)gildong@gmail.com")
    private String email;

    private String username;

    private String password;

}
