package com.GP.GP.roles.Auth.service;


import com.GP.GP.entities.Penalty;
import com.GP.GP.entities.Room;
import com.GP.GP.entities.University;
import com.GP.GP.entities.User;
import com.GP.GP.repository.PenaltyRepository;
import com.GP.GP.repository.UserRepository;
import com.GP.GP.roles.Auth.models.mapper.RegisterMapper;
import com.GP.GP.roles.Auth.models.request.LoginRequestDTO;
import com.GP.GP.roles.Auth.models.request.RegisterRequestDTO;
import com.GP.GP.roles.Auth.models.response.LoginResponseDTO;
import com.GP.GP.roles.Auth.models.response.RegisterResponseDTO;
import com.GP.GP.roles.admin.models.dto.response.UniversityResponseDTO;
import com.GP.GP.roles.admin.service.contracts.RoomAssignmentService;
import com.GP.GP.roles.admin.service.contracts.UniversityService;
import com.GP.GP.security.*;
import com.GP.GP.utill.Enums;
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
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoomAssignmentService roomAssignmentService;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    PenaltyRepository penaltyRepository;
    @Autowired
    private UniversityService universityService;

    public AuthenticationService(
            UserRepository repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            TokenRepository tokenRepository,
            AuthenticationManager authenticationManager,
            PenaltyRepository penaltyRepository,
            RoomAssignmentService roomAssignmentService
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.penaltyRepository = penaltyRepository;
        this.roomAssignmentService = roomAssignmentService;
    }


    public ResponseEntity<Object> register(RegisterRequestDTO request) {
        Optional<User> existingUser = repository.findByUsernameAndNationalId(request.getUsername(), request.getNationalId());

        University university = (request.getRole() == Role.ADMIN || request.getRole() == Role.EDIT_ADMIN || request.getRole() == Role.ViEW_ADMIN)
                ? null
                : universityService.findUniversityById(request.getUniversityId());

        User user = existingUser.map(u -> {
            RegisterMapper.updateUserEntity(u, request, university);
            u.setPassword(passwordEncoder.encode(request.getPassword()));
            return u;
        }).orElseGet(() -> {
            User newUser = RegisterMapper.toUserEntity(request, university);
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
            return newUser;
        });

        boolean isNewStudent = "first".equalsIgnoreCase(request.getLevel());
        String place = request.getPlaceOfBirth() != null ? request.getPlaceOfBirth().trim() : "";
        boolean isFailed = request.getAnnualGrade() == Enums.AnnualGrade.FAIL;

        List<Penalty> penalties = existingUser
                .map(value -> penaltyRepository.findByUserId(value.getId()))
                .orElse(List.of());

        boolean hasPenalty = !penalties.isEmpty();

        boolean isRestrictedPlace = (place.contains("القاهرة") || place.contains("الجيزة") || place.contains("القليوبية"))
                && !(place.contains("كفر شكر") || place.contains("الواحات البحرية"));

        if (!isNewStudent && (isFailed || hasPenalty || isRestrictedPlace)) {
            user.setStatus(Enums.AdmissionRequestStatues.REJECTED);

            String reason;
            if (isRestrictedPlace) {
                reason = "Rejected due to restricted place of birth.";
            } else if (hasPenalty) {
                Penalty firstPenalty = penalties.get(0);
                reason = "Student has a penalty: " + firstPenalty.getPenaltyTitle() + " (" + firstPenalty.getReason() + ")";
            } else {
                reason = "Student has failed the previous academic year.";
            }

            user.setAdmissionRequestStatusNotes(reason);

            if (existingUser.isPresent() && existingUser.get().getRoom() != null) {
                Room oldRoom = existingUser.get().getRoom();
                roomAssignmentService.removeStudentFromRoom(existingUser.get().getId(), oldRoom.getId());
            }

        } else if (isNewStudent && isRestrictedPlace) {
            user.setStatus(Enums.AdmissionRequestStatues.REJECTED);
            user.setAdmissionRequestStatusNotes("Rejected due to restricted place of birth.");

            if (existingUser.isPresent() && existingUser.get().getRoom() != null) {
                Room oldRoom = existingUser.get().getRoom();
                roomAssignmentService.removeStudentFromRoom(existingUser.get().getId(), oldRoom.getId());
            }

        } else {
            user.setStatus(Enums.AdmissionRequestStatues.UNDER_REVIEW);
            user.setSecurityCheck(Enums.SecurityCheckStatues.PENDING);
            user.setAdmissionRequestStatusNotes("Your request is under review.");
            user.setSecurityCheckNotes("Your request is pending until security check done.");
        }

        user = repository.save(user);

        String jwt = jwtService.generateToken(user);
        saveUserToken(jwt, user);

        RegisterResponseDTO responseDTO = RegisterResponseDTO.mapToRegisterResponseDTO(user, jwt, university);
        BaseResponse response = new BaseResponse(true, "User registered successfully", responseDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    public ResponseEntity<Object> login(LoginRequestDTO request) {
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
