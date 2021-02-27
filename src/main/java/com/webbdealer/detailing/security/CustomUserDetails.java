package com.webbdealer.detailing.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CustomUserDetails extends User {

    private com.webbdealer.detailing.employee.dao.User user;

    public CustomUserDetails(
            com.webbdealer.detailing.employee.dao.User user,
            List<? extends GrantedAuthority> grantedAuthorities) {

        super(user.getEmail(), user.getPassword(), grantedAuthorities);
        this.user = user;
    }

    public com.webbdealer.detailing.employee.dao.User getUser() {
        return user;
    }

    public void setUser(com.webbdealer.detailing.employee.dao.User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomUserDetails that = (CustomUserDetails) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user);
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "user=" + user +
                '}';
    }
}
