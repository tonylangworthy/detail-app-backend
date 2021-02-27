package com.webbdealer.detailing.security;

import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.employee.dao.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class PinAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(PinAuthenticationProvider.class);

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public PinAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

//        logger.info("PinAuthenticationProvider.authenticate()");
//        logger.info("PinAuthenticationProvider isAuthenticated?: " + authentication.isAuthenticated());
//
//        String name = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        logger.info("Password: " + password);
//        logger.info(authentication.toString());
//
//        Optional<User> optionalUser = userRepository.findByEmail(name);
//        if(optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            String passwordHash = passwordEncoder.encode(password);
//            boolean isPasswordMatch = passwordEncoder.matches(passwordHash, user.getPassword());
//            if(isPasswordMatch) {
//                return new UsernamePasswordAuthenticationToken(new CustomUserDetails(user, new ArrayList<>()), password, new ArrayList<>());
//            }
//        }

        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
