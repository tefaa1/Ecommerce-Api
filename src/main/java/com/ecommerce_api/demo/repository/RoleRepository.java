package com.ecommerce_api.demo.repository;

import com.ecommerce_api.demo.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role R LEFT JOIN FETCH r.users WHERE r.id = :roleId")
    Optional<Role>findRoleWithUsersByRoleId(@Param("roleId") Long roleId);
} 