package com.ecommerce_api.demo.annotations;

import com.ecommerce_api.demo.model.dto.request.RegisterRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterRequestDTO> {

    @Override
    public boolean isValid(RegisterRequestDTO dto, ConstraintValidatorContext constraintValidatorContext) {

        return dto.getPassword() != null &&
                dto.getConfirmPassword() != null &&
                dto.getPassword().equals(dto.getConfirmPassword());
    }
}
