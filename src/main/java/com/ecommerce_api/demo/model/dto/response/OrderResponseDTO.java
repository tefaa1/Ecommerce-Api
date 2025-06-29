package com.ecommerce_api.demo.model.dto.response;


import com.ecommerce_api.demo.model.dto.slimDto.SlimUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {

    private Long id;

    private SlimUserDTO slimUserDTO;

    private BigDecimal totalPrice;

    private LocalDateTime createdAt;

    private Set<OrderItemResponseDTO> orderItemResponseDTOSet;

}
