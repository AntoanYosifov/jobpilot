package com.antdevrealm.jobpilot.web;

import com.antdevrealm.jobpilot.model.dto.user.UserRegistrationDTO;
import com.antdevrealm.jobpilot.model.dto.user.UserResponseDTO;
import com.antdevrealm.jobpilot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

    @PostMapping()
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRegistrationDTO dto) {
        UserResponseDTO registeredDTO = userService.register(dto);
        URI location = URI.create("/api/users/" + registeredDTO.id());

        return ResponseEntity.created(location)
                .body(registeredDTO);
    }
}
