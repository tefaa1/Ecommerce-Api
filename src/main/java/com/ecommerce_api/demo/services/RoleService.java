package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.entity.Role;
import java.util.List;

public interface RoleService {
    Role saveRole(Role role);
    Role getRoleById(Long id);
    Role getRoleWithUsers(Long id);
    List<Role> getAllRoles();
    void deleteRoleById(Long id);
    void deleteAllRoles();
} 