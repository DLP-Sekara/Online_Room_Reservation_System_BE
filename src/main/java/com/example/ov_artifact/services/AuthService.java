package com.example.ov_artifact.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ov_artifact.dto.AuthDTO;
import com.example.ov_artifact.entity.SystemUsers;
import com.example.ov_artifact.repository.AuthRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService {
    @Autowired
    private AuthRepo authRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(AuthDTO authDTO) {
        if (authRepo.findByName(authDTO.getName()).isPresent()) {
            throw new RuntimeException("Username is already taken!");
        }

        authDTO.setPassword(passwordEncoder.encode(authDTO.getPassword()));
        SystemUsers user = modelMapper.map(authDTO, SystemUsers.class);
        authRepo.save(user);
    }
}
