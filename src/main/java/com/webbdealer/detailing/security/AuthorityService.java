package com.webbdealer.detailing.security;

import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.security.dao.Privilege;
import com.webbdealer.detailing.security.dao.Role;

import java.util.Collection;

public interface AuthorityService {

    Privilege findOrCreatePrivilege(String name);

    Role findOrCreateRole(String name);

    void addPrivilegesToRole(Collection<Privilege> privileges, Role role);

    void addToAdminRole(User user);

    void removeFromAdminRole(User user);

    void addToManagerRole(User user);

    void removeFromManagerRole(User user);

    void addToEmployeeRole(User user);

    void removeFromEmployeeRole(User user);

    boolean roleExists(String roleName);

    boolean privilegeExists(String privilegeName);
}
