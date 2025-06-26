package com.hms.security;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponseDTO {
    private String token;
    private String name;
    private String username;
    private List<Role> roles;

    public LoginResponseDTO(String token, User user) {
        this.token = token;
        this.name = user.getName();
        this.username = user.getUsername();
        this.roles = user.getRoles();
    }
}
