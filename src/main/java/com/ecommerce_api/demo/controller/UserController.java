package com.ecommerce_api.demo.controller;

import com.ecommerce_api.demo.model.dto.request.ChangePasswordRequestDTO;
import com.ecommerce_api.demo.model.dto.request.ChangeUserFullNameRequestDTO;
import com.ecommerce_api.demo.model.dto.slimDto.SlimUserDTO;
import com.ecommerce_api.demo.services.UserService;
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

    @PatchMapping("/changePassword")
    public ResponseEntity<Void>changeThePassword(@RequestBody @Valid ChangePasswordRequestDTO chPassword){

        userService.changePassword(chPassword);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/changeTheName")
    public ResponseEntity<Void>ChangeTheName(@RequestBody @Valid ChangeUserFullNameRequestDTO chName){

        userService.changeTheName(chName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/info")
    public ResponseEntity<SlimUserDTO>getUserInfo(){

        SlimUserDTO slimUserDTO = userService.getUserInfo();
        return ResponseEntity.ok().body(slimUserDTO);
    }

    // For Admins
    @GetMapping("/allUsers")
    public ResponseEntity<List<SlimUserDTO>>getAllUsers(){

        List<SlimUserDTO>slimUserDTOS = userService.getAllUsers();
        return ResponseEntity.ok().body(slimUserDTOS);
    }

    @DeleteMapping("/deleteMyAccount")
    public ResponseEntity<Void>deleteMyAccount(){

        userService.deleteTheUserAccount();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteUserAccount(@PathVariable Long id){

        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
