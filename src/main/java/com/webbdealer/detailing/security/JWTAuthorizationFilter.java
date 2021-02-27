package com.webbdealer.detailing.security;

import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.security.dao.Role;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.webbdealer.detailing.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(HEADER_STRING);

        if(header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        Jws<Claims> jws = null;

        if(token != null) {
            try {
                jws = Jwts.parserBuilder()
                        .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
                        .build()
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
            } catch (JwtException error) {
                logger.error(error.getMessage());
            }

            logger.info("Getting Authentication...");
            logger.info(jws.getBody().toString());

            Long userId = jws.getBody().get("userId", Long.class);
            Long companyId = jws.getBody().get("companyId", Long.class);
            String email = jws.getBody().getSubject();
            List<String> roles = jws.getBody().get("roles", List.class);
            List<String> privileges = jws.getBody().get("privileges", List.class);

            List<GrantedAuthority> authorities = new ArrayList<>();
            privileges.forEach(privilege -> {
                authorities.add(new SimpleGrantedAuthority(privilege));
            });

            JwtClaim jwtClaim = new JwtClaim();
            jwtClaim.setUserId(userId);
            jwtClaim.setCompanyId(companyId);
            jwtClaim.setEmail(email);

            return new UsernamePasswordAuthenticationToken(jwtClaim, null, authorities);
        }

//        if(token != null) {
//            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
//                    .build()
//                    .verify(token.replace(TOKEN_PREFIX, ""))
//                    .getSubject();
//
//            if(user != null) {
//                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
//            }
//            return null;
//        }
        return new UsernamePasswordAuthenticationToken(new User(), null, new ArrayList<>());
    }
}
