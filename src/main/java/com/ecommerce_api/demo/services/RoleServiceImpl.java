package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.entity.Role;
import com.ecommerce_api.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public Role saveRole(Role role) {
        return null;
    }

    @Override
    public Role updateRole(Long id, Role role) {
        return null;
    }

    @Override
    public Role getRoleById(Long id) {
        return null;
    }

    @Override
    public Role getRoleWithUsers(Long id) {
        return null;
    }

    @Override
    public List<Role> getAllRoles() {
        return null;
    }

    @Override
    public void deleteRoleById(Long id) {

    }

    @Override
    public void deleteAllRoles() {

    }
}
