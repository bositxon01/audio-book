package uz.pdp.audiobook.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.service.AuthService;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

import static uz.pdp.audiobook.utils.AuthConstants.BASIC_PREFIX;
import static uz.pdp.audiobook.utils.AuthConstants.BEARER_PREFIX;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        if (path.startsWith("/api/auth/forget-password") ||
                path.startsWith("/api/auth/reset-password")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization != null && authorization.startsWith(BEARER_PREFIX)) {

            String token = authorization.substring(7);

            Claims claims = jwtProvider.extractClaims(token);

            String username = claims.getSubject();

            Optional<User> optionalUser = Optional.ofNullable((User) authService.loadUserByUsername(username));

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                var authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }

        // Basic Auth handling
        else if (Objects.nonNull(authorization) && authorization.startsWith(BASIC_PREFIX)) {
            String basicToken = authorization.substring(6);

            String decodedCredentials = new String(Base64.getDecoder().decode(basicToken));

            String[] credentials = decodedCredentials.split(":");

            if (credentials.length == 2) {
                String username = credentials[0];
                String password = credentials[1];

                Optional<User> optionalUser = Optional.ofNullable((User) authService.loadUserByUsername(username));

                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();

                    if (passwordEncoder.matches(password, user.getPassword())) {
                        var authentication =
                                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                        SecurityContextHolder.getContext()
                                .setAuthentication(authentication);
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
