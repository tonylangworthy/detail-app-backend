package com.webbdealer.detailing.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JwtClaim implements Serializable {

    private Long userId;

    private Long companyId;

    private String email;

    private List<String> roles = new ArrayList<>();

    private List<String> privileges = new ArrayList<>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtClaim jwtClaim = (JwtClaim) o;
        return Objects.equals(userId, jwtClaim.userId) && Objects.equals(companyId, jwtClaim.companyId) && Objects.equals(email, jwtClaim.email) && Objects.equals(roles, jwtClaim.roles) && Objects.equals(privileges, jwtClaim.privileges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, companyId, email, roles, privileges);
    }

    @Override
    public String toString() {
        return "JwtClaim{" +
                "userId=" + userId +
                ", companyId=" + companyId +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", privileges=" + privileges +
                '}';
    }
}
