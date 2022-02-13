package it.mollik.amuse.amusers.service.impl;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

import it.mollik.amuse.amusers.service.IJwtTokenService;
import it.mollik.amuse.amusers.util.AmuseUtils;
import it.mollik.amuse.amusers.util.JwtUtils;

@Service
public class HelperService {

	private static final String[] IP_HEADERS = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR"

        // you can add more matching headers here ...
    };

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private IJwtTokenService jwtTokenService;

	public String getAuthorizazionHeaderValue(String userName, String role) throws Exception {
		if (userName != null && !userName.isEmpty()) {
			return "Bearer " + jwtTokenService.generateTokenV2(userName, role, InetAddress.getLocalHost().getHostAddress(), AmuseUtils.getRandomUserAgent());
		} else {
			throw new Exception("JWT Token null");
		}
	}

	public String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}

	public String getClientIp(WebRequest webRequest) {
		for (String header: IP_HEADERS)  {
            String value = webRequest.getHeader(header);
            if (value == null || value.isEmpty()) {
                continue;
            }
            String[] parts = value.split("\\s*,\\s*");
            return parts[0];
        }
        return org.apache.commons.lang3.StringUtils.EMPTY;
	}

	public String getUserAgent(WebRequest webRequest) {
		String ua = "";
		if (webRequest != null) {
			ua = webRequest.getHeader("User-Agent");
		}
		return ua;
	}
}
