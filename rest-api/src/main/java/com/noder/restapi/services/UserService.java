package com.noder.restapi.services;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.noder.restapi.dtos.UserCreationDTO;
import com.noder.restapi.dtos.UserDTO;
import com.noder.restapi.models.UserEntity;
import com.noder.restapi.repositories.RoleRepostiory;
import com.noder.restapi.repositories.UserRepository;

@Service
public class UserService {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleRepostiory roleRepostiory;

    public UserService(UserRepository userRepository, RoleRepostiory roleRepostiory) {
        this.encoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
        this.roleRepostiory = roleRepostiory;
    }

    public UserEntity saveUser(UserCreationDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setPin(hashPin(userDTO.getPin()));
        // TODO: do more role stuff
        user.setRoles(Collections.singletonList(roleRepostiory.findByName("USER")));
        return userRepository.save(user);
    }

    public UserDTO toUserDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());

        return userDTO;
    }

    public String hashPin(String rawPin) {
        return encoder.encode(rawPin);
    }

    public boolean isPinValid(String rawPin, String hashedPin) {
        return encoder.matches(rawPin, hashedPin);
    }

    public Optional<Long> getUserIdFromAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            currentUserName = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication != null) {
            currentUserName = authentication.getPrincipal().toString();
        }
        if (currentUserName == null) {
            return Optional.empty();
        }
        return userRepository.findByEmail(currentUserName).map(UserEntity::getId);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserEntity> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<UserEntity> getUserFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            currentUserName = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication != null) {
            currentUserName = authentication.getPrincipal().toString();
        }
        if (currentUserName == null) {
            return Optional.empty();
        }
        return userRepository.findByEmail(currentUserName);
        
    }
}
