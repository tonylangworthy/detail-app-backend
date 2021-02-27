package com.webbdealer.detailing.security;

import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.security.dao.Privilege;
import com.webbdealer.detailing.security.dao.PrivilegeRepository;
import com.webbdealer.detailing.security.dao.Role;
import com.webbdealer.detailing.security.dao.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private PrivilegeRepository privilegeRepository;

    private RoleRepository roleRepository;

    @Autowired
    public AuthorityServiceImpl(
            PrivilegeRepository privilegeRepository,
            RoleRepository roleRepository) {
        this.privilegeRepository = privilegeRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Privilege findOrCreatePrivilege(String name) {
        return findPrivilege(name).orElseGet(() -> createPrivilege(name));
    }

    private Optional<Privilege> findPrivilege(String name) {
        return privilegeRepository.findByName(name);
    }

    private Privilege createPrivilege(String name) {
        return privilegeRepository.save(new Privilege(name));
    }

    @Override
    public Role findOrCreateRole(String name) {
        return findRole(name).orElseGet(() -> createRole(name));
    }

    @Override
    public void addPrivilegesToRole(Collection<Privilege> privileges, Role role) {
        privileges.stream().forEach(privilege -> {
            if(!role.getPrivileges().contains(privilege)) {
                role.getPrivileges().add(privilege);
            }
        });
    }

    private Optional<Role> findRole(String name) {
        return roleRepository.findByName(name);
    }

    private Role createRole(String name) {
        return roleRepository.save(new Role(name));
    }

    @Override
    public void addToAdminRole(User user) {
        String roleName = "ROLE_ADMIN";
        addUserToRole(user, roleName);
    }

    @Override
    public void removeFromAdminRole(User user) {

    }

    @Override
    public void addToManagerRole(User user) {
        String roleName = "ROLE_MANAGER";
        addUserToRole(user, roleName);
    }

    @Override
    public void removeFromManagerRole(User user) {

    }

    @Override
    public void addToEmployeeRole(User user) {
        String roleName = "ROLE_DETAILER";
        addUserToRole(user, roleName);
    }

    @Override
    public void removeFromEmployeeRole(User user) {

    }

    @Override
    public boolean roleExists(String roleName) {
        return roleRepository.existsByName(roleName);
    }

    @Override
    public boolean privilegeExists(String privilegeName) {
        return privilegeRepository.existsByName(privilegeName);
    }

    private void addUserToRole(User user, String roleName) {
        Optional<Role> optionalRole = findRole(roleName);
        Role role = optionalRole.orElseThrow(() -> new EntityNotFoundException("Role ["+roleName+"] not found!"));
        user.setRoles(Arrays.asList(role));
    }
}
