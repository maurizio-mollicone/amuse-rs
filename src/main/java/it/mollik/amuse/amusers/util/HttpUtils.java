package it.mollik.amuse.amusers.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import it.mollik.amuse.amusers.model.request.GenericRequest;

@Component
public class HttpUtils<T> {
    
    @Autowired
	private JwtUtils jwtUtils;

    public HttpEntity<GenericRequest> buildRequest(String userName, GenericRequest body) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		if (userName != null && !userName.isEmpty())		{
			String token = jwtUtils.createJwtTestToken(userName);
			headers.set("Authorization", bearerAuthorization(token));
		}
		return new HttpEntity<>(body, headers);
	}
    
	private String bearerAuthorization(String token) {
		return "Bearer " + token;
	}

}
