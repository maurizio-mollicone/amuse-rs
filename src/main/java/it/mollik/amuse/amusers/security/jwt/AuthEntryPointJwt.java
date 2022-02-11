package it.mollik.amuse.amusers.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

import it.mollik.amuse.amusers.model.request.RequestKey;
import it.mollik.amuse.amusers.model.response.JwtResponse;

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
		// final Map<String, Object> body = new HashMap<>();
		// body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		// body.put("error", "Unauthorized");
		// body.put("message", authException.getMessage());
		// body.put("path", request.getServletPath());
		JwtResponse res = new JwtResponse("", 0L, "", "", null);
		res.setRequestKey(new RequestKey("testuser"));
		res.setStatusCode(1);
		res.setStatusMessage("Unauthorized");
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), res);
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}
