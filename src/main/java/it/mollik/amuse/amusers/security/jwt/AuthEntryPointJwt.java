package it.mollik.amuse.amusers.security.jwt;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import it.mollik.amuse.amusers.model.RequestKey;
import it.mollik.amuse.amusers.model.response.AmuseResponse;
import it.mollik.amuse.amusers.model.response.LoginResponse;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException)
			throws IOException, ServletException {
		logger.error("Unauthorized error: {}", authException.getMessage());

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		
		AmuseResponse<LoginResponse> amuseResponse = new AmuseResponse<>(new RequestKey("testuser"), 1, "Unauthorized", Stream.of(new LoginResponse("", 0L, "", "", null)).collect(Collectors.toList()));
		logger.error("Unauthorized {}", amuseResponse);
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), amuseResponse);
	}
}
