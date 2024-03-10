package ru.bankpay.bankpay.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenService jwtTokenService;

    public JwtAuthenticationFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
        throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;

        if (isSwaggerUri(request.getRequestURI())) {
            filterChain.doFilter(req, res);
            return;
        }

        String token = jwtTokenService.resolveToken(request);
        if (token != null && jwtTokenService.validateToken(token)) {
            Authentication auth = jwtTokenService.getAuthentication(token);

            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(req, res);
    }

    private boolean isSwaggerUri(String uri) {
        return uri.startsWith("/swagger-ui/") || uri.startsWith("/v3/api-docs/") || uri.startsWith("/admin-api/");
    }
}
