package io.dietschi.edu.spring_m2m_handson.sample.client.iml;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.dietschi.edu.spring_m2m_handson.sample.client.api.TokenFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
public class Oauth2Fetcher implements TokenFetcher {

        private final RestTemplate restTemplate;
        private final String tokenUrl;
        private final String clientId;
        private final String clientSecret;

        @Override
        public DecodedJWT fetchToken() {

            final HttpEntity<Void> request = new HttpEntity<>(null);
            request.getHeaders().setBasicAuth(clientId, clientSecret);
            request.getHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            request.getHeaders().setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            final TokenResponse response = restTemplate.postForObject(
                    tokenUrl + "?grant_type=client_credentials",
                    request,
                    TokenResponse.class);
            return Optional.ofNullable(response)
                    .map(TokenResponse::accessToken)
                    .map(JWT::decode)
                    .orElseThrow(() -> new IllegalStateException("No token received"));
        }
}
