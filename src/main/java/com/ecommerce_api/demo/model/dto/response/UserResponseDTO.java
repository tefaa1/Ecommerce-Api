package com.ecommerce_api.demo.model.dto.response;

import com.ecommerce_api.demo.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private Role role;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private CartResponseDTO cartResponseDTO;

}
