package com.webbdealer.detailing.security;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbdealer.detailing.employee.dao.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.webbdealer.detailing.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private AuthenticationManager authenticationManager;

    @Autowired
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        logger.info("JWTAuthenticationFilter.attemptAuthentication()");

        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            logger.info("User from Request Input Stream: "+ user.toString());

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword(),
                            new ArrayList<>()
                    )
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        logger.info("JWTAuthenticationFilter.successfulAuthentication()");

        Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        logger.info("User: " + authResult.getPrincipal());

        User user = ((CustomUserDetails) authResult.getPrincipal()).getUser();

        Map<String, Object> claims = new HashMap<>();
        List<String> roles = new ArrayList<>();
        Set<String> privileges = new HashSet<>();
        user.getRoles().forEach(role -> {
            roles.add(role.getName());
            role.getPrivileges().forEach(privilege -> {
                privileges.add(privilege.getName());
            });
        });
        claims.put("userId", user.getId());
        claims.put("companyId", user.getCompany().getId());
        claims.put("firstName", user.getFirstName());
        claims.put("lastname", user.getLastName());
        claims.put("roles", roles);
        claims.put("privileges", privileges);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();

//        String token = JWT.create()
//                .withSubject(((CustomUserDetails) authResult.getPrincipal()).getUsername())
//                .withClaim("id", ((CustomUserDetails) authResult.getPrincipal()).getUser().getId())
//                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .sign(Algorithm.HMAC512(SECRET.getBytes()));

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);

        StringJoiner sj = new StringJoiner(", ", "\"", "\"");
        List<String> testRoles = new ArrayList<>();
        testRoles.add("ROLE_ADMIN");
        testRoles.add("ROLE_MANAGER");

        ObjectMapper objectMapper = new ObjectMapper();
        String roleList = objectMapper.writeValueAsString(roles);

//        String roleList = testRoles.stream().collect(Collectors.joining(", ", "\"", "\""));
//        String roleList = String.join(", ", roles);
        response.getWriter().write("{\"roles\": "+roleList+"}");

    }
}
