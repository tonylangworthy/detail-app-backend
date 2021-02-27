package com.webbdealer.detailing.security;

import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.employee.dao.UserRepository;
import com.webbdealer.detailing.security.dao.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user by username...");
        Optional<User> optionalUser = userRepository.findByEmail(username);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("Email ["+username+"] not found!"));
        return new CustomUserDetails(user, getAuthorities(user));
    }

    private static List<? extends GrantedAuthority> getAuthorities(User user) {
        logger.info("getAuthorities for user email: " + user.getEmail());

        logger.info("roles: " + user.getRoles());
        List<Role> roles = user.getRoles();
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> {
            logger.info("User " + user.getEmail() + " has role " + role.getName());
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPrivileges().stream().map(privilege -> {
                return new SimpleGrantedAuthority(privilege.getName());
            }).forEach(authorities::add);
        });
        return authorities;
    }

}
