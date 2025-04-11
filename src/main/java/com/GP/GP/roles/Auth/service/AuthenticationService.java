package com.GP.GP.roles.Auth.service;


import com.GP.GP.entities.University;
import com.GP.GP.entities.User;
import com.GP.GP.repository.UserRepository;
import com.GP.GP.roles.Auth.models.mapper.RegisterMapper;
import com.GP.GP.roles.Auth.models.request.RegisterRequestDTO;
import com.GP.GP.roles.Auth.models.response.LoginResponseDTO;
import com.GP.GP.roles.Auth.models.response.RegisterResponseDTO;
import com.GP.GP.roles.admin.models.dto.response.UniversityResponseDTO;
import com.GP.GP.roles.admin.service.contracts.UniversityService;
import com.GP.GP.security.*;
import com.GP.GP.utill.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private UniversityService universityService;

    public AuthenticationService(
            UserRepository repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            TokenRepository tokenRepository,
            AuthenticationManager authenticationManager
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }


    public ResponseEntity<Object> register(RegisterRequestDTO request) {

        // Check if username already exists
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            return new ResponseEntity<>(new BaseResponse(false, "User already exists"), HttpStatus.CONFLICT);
        }

        // Find university for non-admin users
        University university = request.getRole() == Role.ADMIN ? null : universityService.findUniversityById(request.getUniversityId());

        // Map user entity
        User user = RegisterMapper.toUserEntity(request, university);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = repository.save(user);

        // Generate JWT token
        String jwt = jwtService.generateToken(user);
        saveUserToken(jwt, user);

        RegisterResponseDTO responseDTO = RegisterResponseDTO.mapToRegisterResponseDTO(user, jwt, university);
        BaseResponse response = new BaseResponse(true, "Admission request updated\"created\" successfully", responseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<Object> login(User request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            User user = repository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String jwt = jwtService.generateToken(user);
            revokeAllTokenByUser(user);
            saveUserToken(jwt, user);

            LoginResponseDTO loginResponseDTO = LoginResponseDTO.mapToResponseDTO(user, jwt);

            return new ResponseEntity<>(new BaseResponse(true, "Login successful", loginResponseDTO), HttpStatus.OK);

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new BaseResponse(false, "Invalid username or password"), HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new BaseResponse(false, "User not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(false, "An unexpected error occurred. Please try again later"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(t -> {
            t.setLoggedOut(true);
        });
        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
