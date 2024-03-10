package ru.bankpay.bankpay.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.bankpay.bankpay.security.jwt.JwtConfigurer;
import ru.bankpay.bankpay.security.jwt.JwtTokenService;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenService jwtTokenService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests(authorize -> authorize
                .requestMatchers(permittedRoutes()).permitAll()
                .anyRequest().authenticated()
            )
            .apply(new JwtConfigurer(jwtTokenService));

        return http.build();
    }

    private RequestMatcher permittedRoutes() {
        return new OrRequestMatcher(
            userRoutes(),
            swaggerRoutes()
        );
    }

    private RequestMatcher userRoutes() {
        return new OrRequestMatcher(
            new AntPathRequestMatcher("/users/register", HttpMethod.POST.name())
        );
    }

    private RequestMatcher swaggerRoutes() {
        return new OrRequestMatcher(
            new AntPathRequestMatcher("/swagger-ui/**", null),
            new AntPathRequestMatcher("/admin-api/**", null),
            new AntPathRequestMatcher("/v3/api-docs/**", null),
            new AntPathRequestMatcher("/v3/api-docs.**", null)
        );
    }
}
