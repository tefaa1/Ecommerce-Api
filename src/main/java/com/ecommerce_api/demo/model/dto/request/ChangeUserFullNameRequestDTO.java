package com.ecommerce_api.demo.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeUserFullNameRequestDTO {

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;
}
