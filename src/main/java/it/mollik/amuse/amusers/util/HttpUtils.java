package it.mollik.amuse.amusers.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class HttpUtils {
    
    @Autowired
	private JwtUtils jwtUtils;

    public HttpEntity<String> buildHeaders(String userName) {
		String token = jwtUtils.createJwtTestToken(userName);
        HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", bearerAuthorization(token));
		return new HttpEntity<String>(headers);
	}
    
	private String bearerAuthorization(String token) {
		return "Bearer " + token;
	}

}
