package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.exception.ResourceNotFoundException;
import com.ecommerce_api.demo.model.dto.request.ChangePasswordRequestDTO;
import com.ecommerce_api.demo.model.dto.request.ChangeUserFullNameRequestDTO;
import com.ecommerce_api.demo.model.dto.request.LoginRequestDTO;
import com.ecommerce_api.demo.model.dto.request.RegisterRequestDTO;
import com.ecommerce_api.demo.model.dto.slimDto.SlimUserDTO;
import com.ecommerce_api.demo.model.entity.User;
import com.ecommerce_api.demo.model.mapper.UserMapper;
import com.ecommerce_api.demo.repository.UserRepository;
import com.ecommerce_api.demo.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JWTUtil jwtUtil,
                          AuthenticationManager authManager, UserMapper userMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with Email " + email + " not found"));

        if(!passwordEncoder.matches(changeDto.getOldPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(changeDto.getNewPassword()));
        userRepository.save(user);

    }

    @Override
    public void changeTheName(ChangeUserFullNameRequestDTO changeName) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with Email " + email + " not found"));

        user.setFirstName(changeName.getFirstName());
        user.setLastName(changeName.getLastName());

        userRepository.save(user);
    }

    @Override
    public String login(LoginRequestDTO request) {

        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());

        authManager.authenticate(authToken);

        return jwtUtil.generateToken(request.getEmail());
    }

    @Override
    public SlimUserDTO getUserInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with Email " + email + " not found"));

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
    }

    @Override
    public void deleteTheUserAccount() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with Email " + email + " not found"));

        userRepository.delete(user);
    }
}
