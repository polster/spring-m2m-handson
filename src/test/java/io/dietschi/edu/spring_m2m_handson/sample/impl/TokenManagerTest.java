package io.dietschi.edu.spring_m2m_handson.sample.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.dietschi.edu.spring_m2m_handson.sample.client.api.TokenFetcher;
import io.dietschi.edu.spring_m2m_handson.sample.client.iml.Oauth2Fetcher;
import io.dietschi.edu.spring_m2m_handson.sample.client.iml.TokenManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class TokenManagerTest {

    @Test
    public void getJwtToken_noTokenInCache_fetchesToken() {

        // given
        final DecodedJWT fakeToken = fakeTokenExpired();
        final TokenFetcher tokenFetcher = mock(Oauth2Fetcher.class);
        final TokenManager tokenManager = new TokenManager(tokenFetcher);
        when(tokenFetcher.fetchToken()).thenReturn(fakeToken);

        // when
        tokenManager.getJwtToken();

        // then
        verify(tokenFetcher).fetchToken();
    }

    @Test
    public void getJwtToken_tokenInCacheExpired_fetchesToken() {

        // given
        final DecodedJWT fakeTokenExpired = fakeTokenExpired();
        final DecodedJWT fakeTokenValid = fakeTokenValid();
        final TokenFetcher tokenFetcher = mock(Oauth2Fetcher.class);
        final TokenManager tokenManager = new TokenManager(tokenFetcher);
        when(tokenFetcher.fetchToken()).thenReturn(fakeTokenExpired, fakeTokenValid);

        // when
        tokenManager.getJwtToken();
        tokenManager.getJwtToken();

        // then
        verify(tokenFetcher, times(2)).fetchToken();
        assertFalse(tokenManager.isTokenExpired());
    }

    @Test
    public void getJwtToken_tokenInCacheValid_returnsToken() {

        // given
        final DecodedJWT fakeTokenValid = fakeTokenValid();
        final TokenFetcher tokenFetcher = mock(Oauth2Fetcher.class);
        final TokenManager tokenManager = new TokenManager(tokenFetcher);
        when(tokenFetcher.fetchToken()).thenReturn(fakeTokenValid);

        // when
        tokenManager.getJwtToken();
        tokenManager.getJwtToken();

        // then
        verify(tokenFetcher, times(1)).fetchToken();
        assertFalse(tokenManager.isTokenExpired());
    }

    private DecodedJWT fakeTokenValid() {
        return JWT.decode("eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJ" +
                "c3N1ZXIiLCJVc2VybmFtZSI6ImphdmEtdGVzdCIsImV4cCI6NDA5MTY5MzAxOCwiaWF0IjoxN" +
                "jkzMzE1NDE4fQ._6vou5SdTOBZ05S21Ef64zzSX701jmM7ag1_iKEgPFk");
    }

    private DecodedJWT fakeTokenExpired() {
        return JWT.decode("eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJ" +
                "c3N1ZXIiLCJVc2VybmFtZSI6ImhhdmEtaGVybyIsImV4cCI6MTY4NTM2NDY1NSwiaWF0IjoxN" +
                "jkzMzEzNDU1fQ.Q05wJrtJF6syYS6hcj3LpmnPutQk-UzSiP_fEwu1huI");
    }
}
