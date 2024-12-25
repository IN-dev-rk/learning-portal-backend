package com.hcl.learning_portal.controller;

import com.hcl.learning_portal.dto.LoginDTO;
import com.hcl.learning_portal.dto.ResponseDTO;
import com.hcl.learning_portal.model.UserModel;
import com.hcl.learning_portal.repository.UserRepository;
import com.hcl.learning_portal.service.AuthService;
import com.hcl.learning_portal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<?>> login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

}
