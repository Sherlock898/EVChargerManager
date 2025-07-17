package com.noder.restapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noder.restapi.dtos.UpdateEmailDTO;
import com.noder.restapi.dtos.UpdatePinDTO;
import com.noder.restapi.dtos.UserDTO;
import com.noder.restapi.models.UserEntity;
import com.noder.restapi.repositories.UserRepository;
import com.noder.restapi.services.UserService;

import jakarta.validation.Valid;


// This class is pending, first we need to implement a way to identify the users.
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUserDetails() {
        UserEntity user = userService.getUserFromAuthentication().orElseThrow(() -> new RuntimeException("User not found"));
        UserDTO userDTO = userService.toUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    private static class UserResponse {
        public String name;
        public String organization;
        public String photoUrl;

        public UserResponse(String name, String organization, String photoUrl) {
            this.name = name;
            this.organization = organization;
            this.photoUrl = photoUrl;
        }
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateEmail(@Valid @RequestBody UpdateEmailDTO dto) {
        try {
            boolean updated = userService.updateUserEmail(dto);
            if (!updated) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("PIN incorrecto.");
            return ResponseEntity.ok("Email actualizado con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/pin")
    public ResponseEntity<?> updatePin(@Valid @RequestBody UpdatePinDTO dto) {
        boolean updated = userService.updateUserPin(dto);
        if (!updated) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("PIN actual incorrecto.");
        return ResponseEntity.ok("PIN actualizado con éxito.");
    }
}
