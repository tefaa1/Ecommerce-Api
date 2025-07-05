package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.model.dto.request.ChangePasswordRequestDTO;
import com.ecommerce_api.demo.model.dto.request.ChangeUserFullNameRequestDTO;
import com.ecommerce_api.demo.model.dto.request.LoginRequestDTO;
import com.ecommerce_api.demo.model.dto.request.RegisterRequestDTO;
import com.ecommerce_api.demo.model.dto.response.AuthenticationResponse;
import com.ecommerce_api.demo.model.dto.response.ProductResponseDTO;
import com.ecommerce_api.demo.model.dto.slimDto.SlimUserDTO;
import com.ecommerce_api.demo.model.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {
    String getTheCurrentUserEmail();
    User getTheCurrentUser();
    User getUserByEmail(String email);
    void register(RegisterRequestDTO request);
    void changePassword (ChangePasswordRequestDTO changeDto);
    void changeTheName(ChangeUserFullNameRequestDTO changeName);
    AuthenticationResponse login(LoginRequestDTO request);
    SlimUserDTO getUserInfo();
    List<SlimUserDTO>getAllUsers();
    void deleteUserById(Long Id);
    void deleteTheUserAccount();
    void addProductToMyWishlist(Long productId);
    void removeProductFromMyWishlist(Long productId);
    List<ProductResponseDTO> getMyWishlist();
    AuthenticationResponse refreshAccessToken(String refreshToken);
} 