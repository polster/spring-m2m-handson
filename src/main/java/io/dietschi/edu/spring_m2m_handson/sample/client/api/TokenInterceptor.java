package io.dietschi.edu.spring_m2m_handson.sample.client.api;

import io.dietschi.edu.spring_m2m_handson.sample.client.iml.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenInterceptor implements ClientHttpRequestInterceptor {

    private final TokenManager tokenManager;

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        request.getHeaders().setBearerAuth(tokenManager.getJwtToken());
        return execution.execute(request, body);
    }
}
