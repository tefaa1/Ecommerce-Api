package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.request.RegisterRequestDTO;
import com.ecommerce_api.demo.model.dto.response.UserResponseDTO;
import com.ecommerce_api.demo.model.dto.slimDto.SlimUserDTO;
import com.ecommerce_api.demo.model.entity.Cart;
import com.ecommerce_api.demo.model.entity.Role;
import com.ecommerce_api.demo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    private final CartMapper cartMapper;

    @Autowired
    public UserMapper(
            PasswordEncoder passwordEncoder,
            CartMapper cartMapper){

        this.passwordEncoder = passwordEncoder;
        this.cartMapper = cartMapper;
    }

    public UserResponseDTO toDto(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .cartResponseDTO(cartMapper.toDto(user.getCart()))
                .build();
    }

    public SlimUserDTO toSlimDto(User user){
        return SlimUserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public User toEntity(RegisterRequestDTO userRequestDTO) {
        return User.builder()
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .email(userRequestDTO.getEmail())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .role(Role.USER)
                .cart(new Cart())
                .build();
    }
} 