package it.mollik.amuse.amusers.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
