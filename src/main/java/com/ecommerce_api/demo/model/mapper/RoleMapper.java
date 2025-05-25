package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.request.RoleRequestDTO;
import com.ecommerce_api.demo.model.dto.response.RoleResponseDTO;
import com.ecommerce_api.demo.model.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public static RoleResponseDTO toDto(Role role) {
        return RoleResponseDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public static Role toEntity(RoleRequestDTO roleRequestDTO) {
        return Role.builder()
                .name(roleRequestDTO.getName())
                .build();
    }
} 