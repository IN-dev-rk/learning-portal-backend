package com.hcl.learning_portal.service;

import com.hcl.learning_portal.dto.LoginDTO;
import com.hcl.learning_portal.dto.ResponseDTO;
import com.hcl.learning_portal.model.UserModel;
import com.hcl.learning_portal.repository.UserRepository;
import com.hcl.learning_portal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<ResponseDTO<?>> login(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = "";
            if (authentication.isAuthenticated()) {
                Optional<UserModel> userModel = userRepository.findByEmail(loginDTO.getEmail());
                if (userModel.isPresent()) {
                    token = jwtUtil.generateToken(userModel.get());
                }
            }
            ResponseDTO<String> responseDTO = new ResponseDTO<>("true", "Token Generated Successfully!", token);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<String> responseDTO = new ResponseDTO<>("false", "Internal Server Error", e.getMessage());
            return ResponseEntity.ok().body(responseDTO);
        }
    }
}
