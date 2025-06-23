package com.example.taskmanager.controller;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    @Value("${app.jwtSecret}") private String secret;
    @Value("${app.jwtExpirationMs}") private Long exp;
    public AuthController(UserRepository r, PasswordEncoder e){repo=r;encoder=e;}
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User req){
        User u = repo.findByUsername(req.getUsername());
        if(u==null||!encoder.matches(req.getPassword(), u.getPassword()))
            return ResponseEntity.status(401).body("Bad credentials");
        String token = Jwts.builder().setSubject(u.getUsername())
            .claim("roles", u.getRole())
            .setExpiration(new Date(System.currentTimeMillis()+exp))
            .signWith(SignatureAlgorithm.HS512, secret).compact();
        return ResponseEntity.ok("{"token":""+token+""}");
    }
}
