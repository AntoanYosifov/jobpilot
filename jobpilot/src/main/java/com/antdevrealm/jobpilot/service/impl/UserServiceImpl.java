package com.antdevrealm.jobpilot.service.impl;

import com.antdevrealm.jobpilot.exception.ResourceNotFoundException;
import com.antdevrealm.jobpilot.model.dto.user.UserRegistrationDTO;
import com.antdevrealm.jobpilot.model.dto.user.UserResponseDTO;
import com.antdevrealm.jobpilot.model.entity.UserEntity;
import com.antdevrealm.jobpilot.repository.user.UserRepository;
import com.antdevrealm.jobpilot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO register(UserRegistrationDTO registrationDTO) {
      return mapToResponseDTO(userRepo.save(mapToEntity(registrationDTO)));
    }

    @Override
    public UserResponseDTO getById(Long id) {
        UserEntity entityById = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with ID: " + id + " does not exist"));
        return mapToResponseDTO(entityById);
    }

    private UserEntity mapToEntity(UserRegistrationDTO registrationDTO) {
       return new UserEntity(registrationDTO.firstName(),
                registrationDTO.lastName(),
                registrationDTO.email(),
                 passwordEncoder.encode(registrationDTO.password()));
    }

    private static UserResponseDTO mapToResponseDTO(UserEntity userEntity) {
        return new UserResponseDTO(userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail());
    }
}
