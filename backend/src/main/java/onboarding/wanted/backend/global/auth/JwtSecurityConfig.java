package onboarding.wanted.backend.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//jwtfilter적용
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private TokenProvider tokenProvider;

    public JwtSecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    //Security 필터 체인에 jwt인증 필터 추가
    @Override
    public void configure(HttpSecurity http) {
        JwtFilter customjwtFilter = new JwtFilter(tokenProvider);
        http.addFilterBefore(customjwtFilter, UsernamePasswordAuthenticationFilter.class);
    }







}
