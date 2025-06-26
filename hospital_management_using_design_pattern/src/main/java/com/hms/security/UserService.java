package com.hms.security;

import com.hms.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;

    public Map<String, String> userResister(User user) {
        try {
            user.setId(Utility.generateId(Utility.USER));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repo.save(user);
            return Map.of("message", "User registered successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public User updateUserCredential(int id, UserCredential userCredential) {
//        Optional<User> userOpt = repo.findById(id);
//        if (userOpt.isEmpty()) return null;
//        User user = userOpt.get();
//
//        String username = userCredential.getUsername();
//        String password = userCredential.getPassword();
//
//        if (username != null && !username.isEmpty()) {
//            user.setUsername(username);
//        }
//        if (password != null && !password.isEmpty()) {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
//        return repo.save(user);
//    }

    public Map<String, Object> verifyUser(UserCredential userCredential) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(userCredential.getUsername(), userCredential.getPassword()));
            String token = jwtService.generateToken(userCredential.getUsername());
            User user = userRepository.findByUsername(userCredential.getUsername());
            user.setPassword(null);
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Login Successful");
            map.put("user", new LoginResponseDTO(token, user));
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
