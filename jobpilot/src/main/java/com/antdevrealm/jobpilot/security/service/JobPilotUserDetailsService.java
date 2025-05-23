package com.antdevrealm.jobpilot.security.service;

import com.antdevrealm.jobpilot.model.entity.UserEntity;
import com.antdevrealm.jobpilot.repository.user.UserRepository;
import com.antdevrealm.jobpilot.security.model.JobPilotUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobPilotUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public JobPilotUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User with an email: " + username + " does not exist!"));

        return mapToUserDetails(userEntity);
    }

    private UserDetails mapToUserDetails(UserEntity entity) {
       return new JobPilotUserDetails(
            entity.getEmail(),
                entity.getPassword(),
                List.of(),
                entity.getId(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName()
        );
    }
}
