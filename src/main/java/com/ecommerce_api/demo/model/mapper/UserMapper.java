package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.request.UserRequestDTO;
import com.ecommerce_api.demo.model.dto.response.UserResponseDTO;
import com.ecommerce_api.demo.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public static UserResponseDTO toDto(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .cartId(user.getCart() != null ? user.getCart().getId() : null)
                .roles(user.getRoles().stream()
                        .map(RoleMapper::toDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static User toEntity(UserRequestDTO userRequestDTO) {
        return User.builder()
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .build();
    }
} 