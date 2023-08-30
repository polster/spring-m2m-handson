package io.dietschi.edu.spring_m2m_handson.sample.api;

import io.dietschi.edu.spring_m2m_handson.sample.client.api.TokenInterceptor;
import io.dietschi.edu.spring_m2m_handson.sample.client.iml.TokenManager;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;

import java.io.IOException;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TokenInterceptorTest {

    private static final String FAKE_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJ" +
            "c3N1ZXIiLCJVc2VybmFtZSI6ImphdmEtdGVzdCIsImV4cCI6NDA5MTY5MzAxOCwiaWF0IjoxN" +
            "jkzMzE1NDE4fQ._6vou5SdTOBZ05S21Ef64zzSX701jmM7ag1_iKEgPFk";

    @Test
    public void intercept_request_addsTokenToRequest() throws IOException {

        // given
        final TokenManager tokenManager = mock(TokenManager.class);
        final TokenInterceptor tokenInterceptor = new TokenInterceptor(tokenManager);
        final HttpRequest request = mock(HttpRequest.class);
        final ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);
        final byte[] body = new byte[0];
        when(tokenManager.getJwtToken()).thenReturn(FAKE_TOKEN);
        when(request.getHeaders()).thenReturn(new HttpHeaders());

        // when
        tokenInterceptor.intercept(request, body, execution);

        // then
        assertEquals(request.getHeaders().get(HttpHeaders.AUTHORIZATION), singletonList("Bearer " + FAKE_TOKEN));
    }
}
