package com.ecommerce_api.demo.controller;

import com.ecommerce_api.demo.model.dto.request.ChangePasswordRequestDTO;
import com.ecommerce_api.demo.model.dto.request.ChangeUserFullNameRequestDTO;
import com.ecommerce_api.demo.model.dto.slimDto.SlimUserDTO;
import com.ecommerce_api.demo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){

        this.userService = userService;
    }

    @PatchMapping("/password")
    public ResponseEntity<?>changeThePassword(@RequestBody @Valid ChangePasswordRequestDTO chPassword){

        userService.changePassword(chPassword);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/name")
    public ResponseEntity<?>ChangeTheName(@RequestBody @Valid ChangeUserFullNameRequestDTO chName){

        userService.changeTheName(chName);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?>getUserInfo(){

        SlimUserDTO slimUserDTO = userService.getUserInfo();
        return ResponseEntity.ok().body(slimUserDTO);
    }

    // For Admins
    @GetMapping("/admin/all")
    public ResponseEntity<?>getAllUsers(){

        List<SlimUserDTO>slimUserDTOS = userService.getAllUsers();
        return ResponseEntity.ok().body(slimUserDTOS);
    }

    @DeleteMapping
    public ResponseEntity<?>deleteMyAccount(){

        userService.deleteTheUserAccount();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?>deleteUserAccount(@PathVariable Long id){

        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
