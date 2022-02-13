package it.mollik.amuse.amusers.service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;

@Service
public interface IJwtTokenService {
    
    /**
     * 
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token);

    /**
     * 
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token);

    /**
     * 
     * @param token
     * @return
     */
    public List<String> getRoles(String token);

    /**
     * 
     * @param <T>
     * @param token
     * @param claimsResolver
     * @return
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);

    /**
     * @deprecated since 0.1
     * 
     * @param authentication
     * @return jwt token
     */
    @Deprecated(since = "0.1", forRemoval = true)
    public String generateToken(Authentication authentication);

    /**
     * 
     * @param authentication
     * @param ipAddress
     * @param userAgent
     * @return
     */
    public String generateTokenV2(Authentication authentication, String ipAddress, String userAgent);

    /**
     * 
     * @param userName
     * @param role
     * @param ipAddress
     * @param userAgent
     * @return
     */
    public String generateTokenV2(String userName, String role, String ipAddress, String userAgent);

    /**
     * 
     * @param token
     * @return
     */
    public Boolean validateToken(String token);

    /**
     * 
     * @param token
     * @return
     */
    public String invalidateToken(String token);

    /**
     * 
     * @param token
     * @return
     */
    public Boolean isInvalid(String token);
}
