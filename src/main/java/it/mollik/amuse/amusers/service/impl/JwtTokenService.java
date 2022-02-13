package it.mollik.amuse.amusers.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.mollik.amuse.amusers.config.Constants;
import it.mollik.amuse.amusers.service.IJwtTokenService;

@Service
public class JwtTokenService implements IJwtTokenService {

    

    private Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

    @Autowired
    private CacheManager cacheManager;
    
    @Value("${amuse.security.jwtSecret:aMuseSecretKey}")
    private String secret;

    @Value("${amuse.security.jwtExpirationMs:86400000}")
    private String validity;

    @Override
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @Override
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    @Override
    public List<String> getRoles(String token) {
        return getClaimFromToken(token, claims -> (List) claims.get(Constants.Jwt.ROLES_CLAIM_KEY));
    }

    @Override
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    
    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    @Override
    public String generateToken(Authentication authentication) {
        final Map<String, Object> claims = new HashMap<>();
        final UserDetails user = (UserDetails) authentication.getPrincipal();

        final List<String> roles = authentication.getAuthorities()
                                                 .stream()
                                                 .map(GrantedAuthority::getAuthority)
                                                 .collect(Collectors.toList());

        claims.put(Constants.Jwt.ROLES_CLAIM_KEY, roles);
        return generateToken(claims, user.getUsername());
    }

    @Override
    public String generateTokenV2(Authentication authentication, String ipAddress, String userAgent) {
        final Map<String, Object> claims = new HashMap<>();
        final UserDetails user = (UserDetails) authentication.getPrincipal();

        final List<String> roles = authentication.getAuthorities()
                                                 .stream()
                                                 .map(GrantedAuthority::getAuthority)
                                                 .collect(Collectors.toList());

        claims.put(Constants.Jwt.ROLES_CLAIM_KEY, roles);
        claims.put(Constants.Jwt.CLIENT_IP_CLAIM_KEY, ipAddress);
        claims.put(Constants.Jwt.USER_AGENT_CLAIM_KEY, userAgent);
        return generateToken(claims, user.getUsername());
    }

    @Override
    public String generateTokenV2(String userName, String role, String ipAddress, String userAgent) {
        
        return createJwtToken(userName, Stream.of(role).collect(Collectors.toList()), ipAddress, userAgent);

    }

    private  String createJwtToken(String userName, List<String> roles, String ipAddress, String userAgent) {
        final Map<String, Object> claims = new HashMap<>();
        

        claims.put(Constants.Jwt.ROLES_CLAIM_KEY, roles);
        claims.put(Constants.Jwt.CLIENT_IP_CLAIM_KEY, ipAddress);
        claims.put(Constants.Jwt.USER_AGENT_CLAIM_KEY, userAgent);
        return generateToken(claims, userName);
    }
    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String generateToken(Map<String, Object> claims, String subject) {
        final long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + Long.parseLong(validity) * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    @Override
    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        return username != null && !isTokenExpired(token) && !isInvalid(token);
    }

    @Override
    public Boolean isInvalid(String token) {
        Cache jwtBlacklist = cacheManager.getCache(Constants.JWT_BLACKLIST_CACHE_KEY);
        if (jwtBlacklist != null) {
            ValueWrapper cachedToken = jwtBlacklist.get(token);
            if (cachedToken != null) {
                logger.warn("token invalid {}", token);
                return token == cachedToken.get();
            } else {
                logger.warn("token valid {}", token);
                return false;
            } 
        } else {
            logger.warn("token valid {}", token);
            return false;
        } 
        
    }

    @Override
    @Cacheable(value = Constants.JWT_BLACKLIST_CACHE_KEY)
    public String invalidateToken(String token){
        return token;
    }

    
}
