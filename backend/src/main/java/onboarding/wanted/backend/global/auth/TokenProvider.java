package onboarding.wanted.backend.global.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
//    private final String secret;
//    private final long tokenValidityInMilliseconds;
    private Key key;

    public TokenProvider(

    ) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
