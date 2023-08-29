package io.dietschi.edu.spring_m2m_handson.sample.client.api;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface TokenFetcher {

        DecodedJWT fetchToken();
}
