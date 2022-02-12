package it.mollik.amuse.amusers.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class HttpUtils {
    
    @Autowired
	private JwtUtils jwtUtils;

    public String getAuthorizazionHeaderValue(String userName) throws Exception {
        if (userName != null && !userName.isEmpty())		{
			return "Bearer " + jwtUtils.createJwtTestToken(userName);
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
}
