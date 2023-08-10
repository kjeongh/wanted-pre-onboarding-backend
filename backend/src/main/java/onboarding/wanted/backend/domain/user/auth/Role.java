package onboarding.wanted.backend.domain.user.auth;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {

    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");
    private String role;

}
