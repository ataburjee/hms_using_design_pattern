package com.hms.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> resisterUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.userResister(user));
    }

    @PostMapping("/login")
    public Map<String, Object> LoginUser(@RequestBody UserCredential userCredential) {
        return this.userService.verifyUser(userCredential);
    }
}