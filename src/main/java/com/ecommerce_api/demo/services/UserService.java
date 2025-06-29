package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.model.dto.request.ChangePasswordRequestDTO;
import com.ecommerce_api.demo.model.dto.request.ChangeUserFullNameRequestDTO;
import com.ecommerce_api.demo.model.dto.request.LoginRequestDTO;
import com.ecommerce_api.demo.model.dto.request.RegisterRequestDTO;
import com.ecommerce_api.demo.model.dto.response.UserResponseDTO;
import com.ecommerce_api.demo.model.dto.slimDto.SlimUserDTO;
import com.ecommerce_api.demo.model.entity.User;
import java.util.List;

public interface UserService {
    void register(RegisterRequestDTO request);
    void changePassword (ChangePasswordRequestDTO changeDto);
    void changeTheName(ChangeUserFullNameRequestDTO changeName);
    String login(LoginRequestDTO request);
    SlimUserDTO getUserInfo();
    List<SlimUserDTO>getAllUsers();
    void deleteUserById(Long Id);
    void deleteTheUserAccount();
} 