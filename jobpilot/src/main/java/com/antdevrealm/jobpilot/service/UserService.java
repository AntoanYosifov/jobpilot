package com.antdevrealm.jobpilot.service;

import com.antdevrealm.jobpilot.model.dto.user.UserLoginDTO;
import com.antdevrealm.jobpilot.model.dto.user.UserRegistrationDTO;
import com.antdevrealm.jobpilot.model.dto.user.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO register(UserRegistrationDTO registerDTO);

    UserResponseDTO getById(Long id);

    List<UserResponseDTO> getAll();

    boolean validateUser(UserLoginDTO loginDTO);
}
