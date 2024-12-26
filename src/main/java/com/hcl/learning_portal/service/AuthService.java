package com.hcl.learning_portal.service;

import com.hcl.learning_portal.constant.Constants;
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
            if(!authentication.isAuthenticated()) {
                ResponseDTO<String> responseDTO = new ResponseDTO<>(401, Constants.BAD_CREDENTIALS_MESSAGE, "");
                return ResponseEntity.status(401).body(responseDTO);
            }
            if (authentication.isAuthenticated()) {
                Optional<UserModel> userModel = userRepository.findByEmail(loginDTO.getEmail());
                if (userModel.isPresent()) {
                    token = jwtUtil.generateToken(userModel.get());
                }
            }
            ResponseDTO<?> responseDTO = new ResponseDTO<>(200, Constants.TOKEN_GENERATE_MESSAGE, token);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<?> responseDTO = new ResponseDTO<>(500, e.getMessage(), null);
            return ResponseEntity.ok().body(responseDTO);
        }
    }
}
