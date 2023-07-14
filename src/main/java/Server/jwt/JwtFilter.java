package Server.jwt;

import Server.entity.AccessToken;
import Server.entity.MyUserDetail;
import Server.entity.User;
import Server.repository.AccessTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AccessTokenRepository accessTokenRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.substring(7);
        String username = jwtService.getUsername(token);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            AccessToken accessToken = accessTokenRepository.findByAccessToken(token);
            if(accessToken != null && accessToken.getTimeExpire().getTime() > System.currentTimeMillis()){
                User user = accessToken.getUser();
                UserDetails userDetails = new MyUserDetail(user.getUsername(),user.getPassword(), user.getRoles());
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            else{
                System.out.println("Token revoked");
                filterChain.doFilter(request,response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
