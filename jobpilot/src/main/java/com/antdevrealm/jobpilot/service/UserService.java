package com.antdevrealm.jobpilot.service;

import com.antdevrealm.jobpilot.model.dto.user.UserRegistrationDTO;
import com.antdevrealm.jobpilot.model.dto.user.UserResponseDTO;

public interface UserService {
    UserResponseDTO register(UserRegistrationDTO registerDTO);
}
