package com.hms.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCredential {
    private String username;
    private String password;
}
