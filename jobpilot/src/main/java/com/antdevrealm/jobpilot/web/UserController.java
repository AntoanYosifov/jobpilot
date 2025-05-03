package com.antdevrealm.jobpilot.web;

import com.antdevrealm.jobpilot.model.dto.user.UserLoginDTO;
import com.antdevrealm.jobpilot.model.dto.user.UserRegistrationDTO;
import com.antdevrealm.jobpilot.model.dto.user.UserResponseDTO;
import com.antdevrealm.jobpilot.service.UserService;
import com.antdevrealm.jobpilot.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // TODO: Add Pageable functionality and search for administration purposes and data integrity
    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<UserResponseDTO> responseDTOS = userService.getAll();
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        UserResponseDTO responseDTO = userService.getById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRegistrationDTO dto) {
        UserResponseDTO registeredDTO = userService.register(dto);
        URI location = URI.create("/api/users/" + registeredDTO.id());

        return ResponseEntity.created(location)
                .body(registeredDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDTO loginDTO) {
        boolean isValid = userService.validateUser(loginDTO);

        Map<String, String> response = new HashMap<>();

        if (isValid) {
            String token = jwtUtil.generateToken(loginDTO.email());
            response.put("token", token);

            return ResponseEntity.ok(response);
        }

        response.put("error", "Invalid Credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
