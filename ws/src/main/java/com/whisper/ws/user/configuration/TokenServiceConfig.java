package com.whisper.ws.user.configuration;

import com.whisper.ws.auth.service.OpaqueTokenService;
import com.whisper.ws.auth.service.TokenService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "whisper.token-type", havingValue = "opaque")
public class TokenServiceConfig {

    @Bean
    public TokenService opaqueTokenService() {
        return new OpaqueTokenService();
    }
}