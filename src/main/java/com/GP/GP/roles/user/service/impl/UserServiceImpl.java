package com.GP.GP.roles.user.service.impl;

import com.GP.GP.entities.University;
import com.GP.GP.repository.AccommodationRepository;
import com.GP.GP.repository.AdmissionRequestRepository;
import com.GP.GP.roles.admin.service.contracts.UniversityService;
import com.GP.GP.roles.user.model.dto.StudentDto;
import com.GP.GP.roles.user.model.dto.StudentPriorityDto;
import com.GP.GP.roles.user.model.mapper.*;
import com.GP.GP.roles.user.model.request.UpdateUserRequestDTO;
import com.GP.GP.roles.user.model.request.UserFilterDTO;
import com.GP.GP.roles.user.model.response.AdmissionRequestInquiryResponseDTO;
import com.GP.GP.roles.user.model.response.StudentsGroupedResponseDTO;
import com.GP.GP.roles.user.model.response.UpdatedUserResponseDTO;
import com.GP.GP.roles.user.service.contracts.AdmissionRequestService;
import com.GP.GP.entities.AdmissionRequest;
import com.GP.GP.entities.User;
import com.GP.GP.repository.UserRepository;
import com.GP.GP.roles.user.model.dto.AdmissionRequestDTO;
import com.GP.GP.roles.user.exception.InvalidOperationException;
import com.GP.GP.roles.user.exception.ResourceNotFoundException;
import com.GP.GP.security.Role;
import com.GP.GP.utill.Enums;
import com.GP.GP.utill.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements AdmissionRequestService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private AdmissionRequestRepository admissionRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UniversityService universityService;
    @Autowired
    AccommodationRepository accommodationRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    // no longer needed
    @Override
    public ResponseEntity<Object> createAdmissionRequest(AdmissionRequestDTO admissionRequestDTO) {
        User user = userRepository.findById(admissionRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with " + admissionRequestDTO.getUserId()));
        University university = universityService.findUniversityById(admissionRequestDTO.getUniversityId());
        AdmissionRequest admissionRequest;

        admissionRequest = AdmissionRequestMapper.toEntity(admissionRequestDTO, user, university);

        // why do you need to put it in the DTO !!
        admissionRequest.setStatus(Enums.AdmissionRequestStatues.UNDER_REVIEW);
        admissionRequest.setCreatedAt(LocalDateTime.now());

        AdmissionRequest savedRequest = admissionRequestRepository.save(admissionRequest);

        AdmissionRequestDTO dto = AdmissionRequestMapper.toDTO(savedRequest);
        BaseResponse response = new BaseResponse(true, "Admission request created successfully", dto);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateUser(int id, UpdateUserRequestDTO updateUserRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admission request not found"));

        if (existingUser.getStatus() != Enums.AdmissionRequestStatues.UNDER_REVIEW) {
            throw new InvalidOperationException("Cannot update request after it has been processed");
        }

        University university = universityService.findUniversityById(updateUserRequestDTO.getUniversityId());

        UserMapper.updateUserEntity(existingUser, updateUserRequestDTO, university, passwordEncoder);

        User updatedUser = userRepository.save(existingUser);

        UpdatedUserResponseDTO responseDTO = UserMapper.mapToUpdatedUserResponseDTO(updatedUser);
        BaseResponse response = new BaseResponse(true, "Admission request updated successfully", responseDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Object> checkApplicationStatus(int id, int userId) {
        User request = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Admission request not found"));
        String admissionRequestStatues = request.getStatus().name();
        BaseResponse response = new BaseResponse(true, "Application status retrieved successfully", admissionRequestStatues);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllAdmissionRequests(List<User> filteredRequests) {
        List<UpdatedUserResponseDTO> dtos = filteredRequests.stream()
                .map(UserMapper::mapToUpdatedUserResponseDTO)
                .collect(Collectors.toList());

        BaseResponse response = new BaseResponse(true, "All admission requests retrieved successfully", dtos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAdmissionRequestByUserId(int userId) {
        User request = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Admission request not found for userId: " + userId));

        UpdatedUserResponseDTO dto = UserMapper.mapToUpdatedUserResponseDTO(request);
        BaseResponse response = new BaseResponse(true, "Admission request retrieved successfully", dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAdmissionRequestById(int id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedInUsername;
        if (principal instanceof UserDetails) {
            loggedInUsername = ((UserDetails) principal).getUsername();
        } else {
            loggedInUsername = principal.toString();
        }

        User loggedInUser = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        User request = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admission request not found for id: " + id));
        if (!loggedInUser.getId().equals(request.getId())) {
            BaseResponse response = new BaseResponse(false, "You are not authorized to access this application.", null);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        UpdatedUserResponseDTO dto = UserMapper.mapToUpdatedUserResponseDTO(request);
        BaseResponse response = new BaseResponse(true, "Admission request retrieved successfully", dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateAdmissionRequestStatues(int id, Enums.AdmissionRequestStatues status) {
        if (status == null) {
            return new ResponseEntity<>(new BaseResponse(false, "Status cannot be null", null), HttpStatus.BAD_REQUEST);
        }


        if (!userRepository.existsById(id)) {
            BaseResponse response = new BaseResponse(false, "Admission request not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        User existingRequest = userRepository.findById(id).get();

        if (existingRequest.getStatus() == status) {
            return new ResponseEntity<>(new BaseResponse(false, "Admission request is already in the requested state", null), HttpStatus.BAD_REQUEST);

        }

        existingRequest.setStatus(status);

        User updatedRequest = userRepository.save(existingRequest);
        UpdatedUserResponseDTO responseDTO = UserMapper.mapToUpdatedUserResponseDTO(updatedRequest);
        BaseResponse response = new BaseResponse(true, "Admission request updated successfully", responseDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Object> getApplicationStatusByNID(String nationalId) {

        User admissionRequest = userRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new ResourceNotFoundException("No admission request found for National ID: " + nationalId));

        AdmissionRequestInquiryResponseDTO responseDTO = AdmissionRequestInquiryMapper.entityToResponse(admissionRequest);
        BaseResponse response = new BaseResponse(true, "Application status retrieved successfully", responseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public List<User> filterAdmissionRequests(UserFilterDTO filterDTO) {
        List<Role> adminRoles = List.of(Role.ADMIN, Role.EDIT_ADMIN, Role.ViEW_ADMIN);
        List<User> requests = userRepository.findByRoleNotIn(adminRoles);
        return requests.stream()
                .filter(user -> filterDTO.getStatus() == null || filterDTO.getStatus().contains(user.getStatus()))
                .filter(user -> filterDTO.getSecurityCheck() == null || filterDTO.getSecurityCheck().contains(user.getSecurityCheck()))
                .filter(user -> filterDTO.getHasPenalty() == null
                        || (filterDTO.getHasPenalty() ? user.getPenalties() != null && !user.getPenalties().isEmpty()
                        : user.getPenalties() == null || user.getPenalties().isEmpty()))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Object> getSortedApplicants() {
        Map<String, Double> governorateDistances = Map.ofEntries(
                Map.entry("Giza", 19.27),
                Map.entry("Cairo", 20.13),
                Map.entry("Qalyubia", 51.68),
                Map.entry("Fayoum", 77.10),
                Map.entry("Menoufia", 86.96),
                Map.entry("Beni Suef", 88.41),
                Map.entry("Beheira", 89.45),
                Map.entry("Sharqia", 103.44),
                Map.entry("Suez", 119.56),
                Map.entry("Ismailia", 123.03),
                Map.entry("Gharbia", 125.80),
                Map.entry("Dakahlia", 131.30),
                Map.entry("Kafr El Sheikh", 142.67),
                Map.entry("Damietta", 178.39),
                Map.entry("Port Said", 181.54),
                Map.entry("Alexandria", 199.70),
                Map.entry("Minya", 202.43),
                Map.entry("North Sinai", 230.58),
                Map.entry("Assiut", 298.04),
                Map.entry("South Sinai", 322.55),
                Map.entry("Sohag", 369.52),
                Map.entry("Red Sea", 378.38),
                Map.entry("Matrouh", 424.49),
                Map.entry("Qena", 433.74),
                Map.entry("Luxor", 481.17),
                Map.entry("New Valley", 495.46),
                Map.entry("Aswan", 659.20)
        );

        List<User> users = userRepository.findAll().stream()
                .filter(user -> user.getSecurityCheck() == Enums.SecurityCheckStatues.ACCEPTED)
                .filter(this::shouldIncludeUser)
                .collect(Collectors.toList());

        List<User> newUsers = users.stream()
                .filter(user -> "First Year".equalsIgnoreCase(user.getLevel()))
                .collect(Collectors.toList());

        List<User> oldUsers = users.stream()
                .filter(user -> !"First Year".equalsIgnoreCase(user.getLevel()))
                .collect(Collectors.toList());

        newUsers.sort((u1, u2) -> {
            int gradeComparison = Float.compare(u2.getTotalGradesHighSchool(), u1.getTotalGradesHighSchool());
            if (gradeComparison != 0) return gradeComparison;

            int ageComparison = u1.getDateOfBirth().compareTo(u2.getDateOfBirth());
            if (ageComparison != 0) return ageComparison;

            double dist1 = governorateDistances.getOrDefault(u1.getResidenceAddress(), 0.0);
            double dist2 = governorateDistances.getOrDefault(u2.getResidenceAddress(), 0.0);
            return Double.compare(dist2, dist1);
        });

        oldUsers.sort((u1, u2) -> {
            int levelDiff = Integer.compare(getLevelDiff(u2.getLevel()), getLevelDiff(u1.getLevel()));
            if (levelDiff != 0) return levelDiff;

            int gpaComparison = Double.compare(u2.getPreviousAcademicYearGpa(), u1.getPreviousAcademicYearGpa());
            if (gpaComparison != 0) return gpaComparison;

            int ageComparison = u1.getDateOfBirth().compareTo(u2.getDateOfBirth());
            if (ageComparison != 0) return ageComparison;

            double dist1 = governorateDistances.getOrDefault(u1.getResidenceAddress(), 0.0);
            double dist2 = governorateDistances.getOrDefault(u2.getResidenceAddress(), 0.0);
            return Double.compare(dist2, dist1);
        });

        List<StudentDto> oldStudentDtos = newUsers.stream().map(StudentMapper::toDto).collect(Collectors.toList());
        List<StudentDto> newStudentDtos = oldUsers.stream().map(StudentMapper::toDto).collect(Collectors.toList());

        StudentsGroupedResponseDTO groupedResponse = new StudentsGroupedResponseDTO(newStudentDtos, oldStudentDtos);
        BaseResponse response = new BaseResponse(true, "Applicants sorted successfully", groupedResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private boolean shouldIncludeUser(User user) {
        String address = user.getResidenceAddress();
        if (address == null) return false;
        String normalized = address.toLowerCase();

        if (normalized.contains("الواحات") || normalized.contains("el wahat")) return true;
        if (normalized.contains("كفر شكر") || normalized.contains("kafr shukr")) return true;

        if (normalized.contains("الجيزة") || normalized.contains("giza")) return false;
        if (normalized.contains("القاهرة") || normalized.contains("cairo")) return false;
        if (normalized.contains("القليوبية") || normalized.contains("qalyubia")) return false;

        return true;
    }

    private int getLevelDiff(String level) {
        return switch (level.toLowerCase()) {
            case "first year" -> 1;
            case "second year" -> 2;
            case "third year" -> 3;
            case "fourth year" -> 4;
            default -> 0;
        };
    }


}

