package Server.jwt;

import Server.entity.AccessToken;
import Server.entity.RefreshToken;
import Server.entity.User;
import Server.repository.AccessTokenRepository;
import Server.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final String  SECRET_ACCESS_KEY = "PnuEG47l6x9uicylUXjfUWMLipbDeaN5DExK5bV6IbKyM4rrpNkwXijcZV7EZJmpQl6x9uicylUXjfUWMLipbDeaN5DExK5bV6IbKyM4rrpNkwXijcZV1bzEu0RuC3wv6";
    private final String SECRET_REFRESH_KEY = "l6x9uicylUXjfUWMLipbDeaN5DExK5bV6IbKyM4rrpNkwXijcZV1bzEu0RuC3wv6cylUXjfUWMLipbDeaN5DExK5bV6IbKyM4rrpNkishdgistgiwIGSdiyDIFDGIFSgi";
    private final long TIME_ACCESS_TOKEN = 60 * 60 * 1000;
    private final long TIME_REFRESH_TOKEN = 6 * 2592000000L;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    public String generateAccessToken(String username){
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSignInKey(SECRET_ACCESS_KEY), SignatureAlgorithm.HS512)
                .compact();
    }
    public String generateRefreshToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSignInKey(SECRET_REFRESH_KEY), SignatureAlgorithm.HS512)
                .compact();
    }
    public String getUsername(String token){
        Claims claims = Jwts.parser().setSigningKey(getSignInKey(SECRET_ACCESS_KEY)).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    private Key getSignInKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    public void saveAccessToken(String accessToken, User user){
        accessTokenRepository.save(
                AccessToken.builder()
                        .accessToken(accessToken)
                        .timeExpire(new Date(System.currentTimeMillis() + TIME_ACCESS_TOKEN))
                        .user(user)
                        .build()
        );
    }
    public void revokeAccessToken(User user){
        accessTokenRepository.deleteByUserId(user.getId());
    }
    public void saveRefreshToken(String refreshToken, User user){
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .refreshToken(refreshToken)
                        .timeExpire(new Date(System.currentTimeMillis() + TIME_REFRESH_TOKEN))
                        .build()
        );
    }
    public void revokeRefreshToken(User user){
        refreshTokenRepository.deleteByUserId(user.getId());
    }
}
