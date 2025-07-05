package com.ecommerce_api.demo.services;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ecommerce_api.demo.exception.ResourceNotFoundException;
import com.ecommerce_api.demo.model.dto.request.ChangePasswordRequestDTO;
import com.ecommerce_api.demo.model.dto.request.ChangeUserFullNameRequestDTO;
import com.ecommerce_api.demo.model.dto.request.LoginRequestDTO;
import com.ecommerce_api.demo.model.dto.request.RegisterRequestDTO;
import com.ecommerce_api.demo.model.dto.response.AuthenticationResponse;
import com.ecommerce_api.demo.model.dto.response.ProductResponseDTO;
import com.ecommerce_api.demo.model.dto.slimDto.SlimUserDTO;
import com.ecommerce_api.demo.model.entity.Product;
import com.ecommerce_api.demo.model.entity.User;
import com.ecommerce_api.demo.model.mapper.ProductMapper;
import com.ecommerce_api.demo.model.mapper.UserMapper;
import com.ecommerce_api.demo.repository.ProductRepository;
import com.ecommerce_api.demo.repository.UserRepository;
import com.ecommerce_api.demo.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final TokenService tokenService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JWTUtil jwtUtil,
                           AuthenticationManager authManager, UserMapper userMapper,
                           PasswordEncoder passwordEncoder,ProductRepository productRepository,
                           ProductMapper productMapper,TokenService tokenService) {

        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.productRepository = productRepository;
        this.productMapper =productMapper;
        this.tokenService = tokenService;
    }

    public String getTheCurrentUserEmail(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public User getTheCurrentUser(){

        return getUserByEmail(getTheCurrentUserEmail());
    }

    @Override
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with Email " + email + " not found"));
    }

    @Override
    public void register(RegisterRequestDTO request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already in use");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void changePassword(ChangePasswordRequestDTO changeDto) {

        User user = getTheCurrentUser();

        if(!passwordEncoder.matches(changeDto.getOldPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(changeDto.getNewPassword()));
        userRepository.save(user);

    }

    @Override
    public void changeTheName(ChangeUserFullNameRequestDTO changeName) {

        User user = getTheCurrentUser();

        user.setFirstName(changeName.getFirstName());
        user.setLastName(changeName.getLastName());

        userRepository.save(user);
    }

    @Override
    public AuthenticationResponse login(LoginRequestDTO request) {

        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());

        authManager.authenticate(authToken);

        String AccessToken = jwtUtil.generateAccessToken(request.getEmail());
        String RefreshToken = jwtUtil.generateRefreshToken(request.getEmail());

        long expirationMillisOfRT = jwtUtil.getRemainingMillis(RefreshToken);
        long expirationMillisOfAT = jwtUtil.getRemainingMillis(AccessToken);

        tokenService.saveRefreshToken(request.getEmail(), RefreshToken,expirationMillisOfRT);
        tokenService.saveAccessToken(request.getEmail(),AccessToken,expirationMillisOfAT);

        return AuthenticationResponse
                .builder()
                .accessToken(AccessToken)
                .refreshToken(RefreshToken)
                .build();
    }

    @Override
    public SlimUserDTO getUserInfo() {

        User user = getTheCurrentUser();

        return userMapper.toSlimDto(user);
    }

    @Override
    public List<SlimUserDTO> getAllUsers() {

        List<User>users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toSlimDto)
                .collect(Collectors.toList());
    }

    // For Admins
    @Override
    public void deleteUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));

        userRepository.delete(user);

        String email = user.getEmail();
        blackListTheUserTokens(email);
    }

    @Override
    public void deleteTheUserAccount() {

        User user = getTheCurrentUser();
        userRepository.delete(user);

        String email = user.getEmail();
        blackListTheUserTokens(email);
    }

    private void blackListTheUserTokens(String email){

        String accessToken = tokenService.getAccessToken(email);
        String refreshToken = tokenService.getRefreshToken(email);

        tokenService.deleteAccessToken(accessToken);
        tokenService.deleteRefreshToken(refreshToken);

        long ATMillis = jwtUtil.getRemainingMillis(accessToken);
        long RTMillis = jwtUtil.getRemainingMillis(refreshToken);

        tokenService.blacklistAccessToken(accessToken, ATMillis);
        tokenService.blacklistRefreshToken(refreshToken, RTMillis);
    }

    @Override
    public void addProductToMyWishlist(Long productId) {

        User user = getTheCurrentUser();

        Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("User with ID " + productId + " not found"));

        if(user.getWishlist().contains(product)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product is already in your wishlist");
        }

        user.getWishlist().add(product);
        userRepository.save(user);
    }

    @Override
    public void removeProductFromMyWishlist(Long productId) {

        User user = getTheCurrentUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + productId + " not found"));

        if(!user.getWishlist().contains(product)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No product in your wishlist with that id");
        }

        user.getWishlist().remove(product);
        userRepository.save(user);
    }

    @Override
    public List<ProductResponseDTO> getMyWishlist(){

        User user = getTheCurrentUser();
        return user.getWishlist().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthenticationResponse refreshAccessToken(String refreshToken) {

        if (!jwtUtil.isTokenValid(refreshToken)) {
            throw new JWTVerificationException("Token expired or invalid");
        }

        if (jwtUtil.isAccessToken(refreshToken)) {
            throw new JWTVerificationException("Access token is not allowed here");
        }

        String email = jwtUtil.extractEmail(refreshToken);

        if (!tokenService.isRefreshTokenValid(email, refreshToken)) {
            throw new JWTVerificationException("Token mismatch or revoked");
        }

        String newAccessToken = jwtUtil.generateAccessToken(email);
        long millis = jwtUtil.getRemainingMillis(newAccessToken);

        tokenService.saveAccessToken(email,newAccessToken,millis);

        return new AuthenticationResponse(newAccessToken, refreshToken);
    }
}
