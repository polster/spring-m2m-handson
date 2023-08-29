package io.dietschi.edu.spring_m2m_handson.sample.client.iml;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.dietschi.edu.spring_m2m_handson.sample.client.api.TokenFetcher;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
public class TokenManager {

    private final TokenFetcher tokenFetcher;
    private DecodedJWT jwt;

    public String getJwtToken() {

        Optional.ofNullable(jwt)
                .ifPresentOrElse(
                        (token) -> {
                            jwt = isTokenExpired() ? tokenFetcher.fetchToken() : token;
                        },
                        () -> {
                            jwt = tokenFetcher.fetchToken();
                        }
                );
        return jwt.getToken();
    }

    public boolean isTokenExpired() {
        return jwt.getExpiresAt().before(new Date());
    }
}
