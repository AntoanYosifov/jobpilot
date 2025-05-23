package com.antdevrealm.jobpilot.security.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
@Getter
public class JobPilotUserDetails extends User {

    private long id;
    private String email;
    private String firstName;
    private String lastName;

    public JobPilotUserDetails(String username,
                               String password,
                               Collection<? extends GrantedAuthority> authorities,
                               long id,
                               String email,
                               String firstName,
                               String lastName) {
        super(username, password, authorities);

        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
