package it.mollik.amuse.amusers.service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;

@Service
public interface IJwtTokenService {
    
    public String getUsernameFromToken(String token);

    public Date getExpirationDateFromToken(String token);

    public List<String> getRoles(String token);

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);

    public String generateToken(Authentication authentication);

    public Boolean validateToken(String token);

    public String invalidateToken(String token);

    public Boolean isInvalid(String token);
}
