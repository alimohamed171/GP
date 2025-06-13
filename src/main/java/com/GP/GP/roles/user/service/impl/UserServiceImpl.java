package com.GP.GP.roles.user.service.impl;

import com.GP.GP.entities.University;
import com.GP.GP.repository.AccommodationRepository;
import com.GP.GP.repository.AdmissionRequestRepository;
import com.GP.GP.roles.admin.models.dto.request.AdmissionStatusNotesDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
            BaseResponse response = new BaseResponse(false, "Cannot update request after it has been processed", null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
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
    public ResponseEntity<Object> updateAdmissionRequestStatues(int id, Enums.AdmissionRequestStatues status, AdmissionStatusNotesDTO statusNotes) {
        if (status == null) {
            return new ResponseEntity<>(new BaseResponse(false, "Status cannot be null", null), HttpStatus.BAD_REQUEST);
        }
        if (statusNotes.getAdmissionStatusNotes() == null || statusNotes.getAdmissionStatusNotes().trim().isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(false, "Status note cannot be empty", null), HttpStatus.BAD_REQUEST);
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
        existingRequest.setAdmissionRequestStatusNotes(statusNotes.getAdmissionStatusNotes());

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
    public Page<User> filterAdmissionRequests(UserFilterDTO filterDTO, Pageable pageable) {
        List<Role> adminRoles = List.of(Role.ADMIN, Role.EDIT_ADMIN, Role.ViEW_ADMIN);
        Specification<User> spec = UserSpecification.filterBy(filterDTO, adminRoles);
        return userRepository.findAll(spec, pageable);
    }

    // Get only admins with optional filters
    @Override
    public Page<User> filterAdmins(UserFilterDTO filterDTO, Pageable pageable) {
        List<Role> adminRoles = List.of(Role.ADMIN, Role.EDIT_ADMIN, Role.ViEW_ADMIN);
        Specification<User> spec = Specification
                .where(UserSpecification.includeOnlyRoles(adminRoles))
                .and(UserSpecification.filterByUserFilterDTO(filterDTO));

        return userRepository.findAll(spec, pageable);
    }

    @Override
    public StudentsGroupedResponseDTO getSortedApplicantsData() {
        Map<String, Double> governorateDistances = Map.ofEntries(
                Map.entry("الجيزة", 19.27),
                Map.entry("القاهرة", 20.13),
                Map.entry("القليوبية", 51.68),
                Map.entry("الفيوم", 77.10),
                Map.entry("المنوفية", 86.96),
                Map.entry("بني سويف", 88.41),
                Map.entry("البحيرة", 89.45),
                Map.entry("الشرقية", 103.44),
                Map.entry("السويس", 119.56),
                Map.entry("الإسماعيلية", 123.03),
                Map.entry("الغربية", 125.80),
                Map.entry("الدقهلية", 131.30),
                Map.entry("كفر الشيخ", 142.67),
                Map.entry("دمياط", 178.39),
                Map.entry("بورسعيد", 181.54),
                Map.entry("الإسكندرية", 199.70),
                Map.entry("المنيا", 202.43),
                Map.entry("شمال سيناء", 230.58),
                Map.entry("أسيوط", 298.04),
                Map.entry("جنوب سيناء", 322.55),
                Map.entry("سوهاج", 369.52),
                Map.entry("البحر الأحمر", 378.38),
                Map.entry("مطروح", 424.49),
                Map.entry("قنا", 433.74),
                Map.entry("الأقصر", 481.17),
                Map.entry("الوادي الجديد", 495.46),
                Map.entry("أسوان", 659.20)
        );

        List<User> users = userRepository.findAll().stream()
                .filter(user -> user.getStatus() == Enums.AdmissionRequestStatues.UNDER_REVIEW)
                .filter(user -> user.getSecurityCheck() == Enums.SecurityCheckStatues.ACCEPTED)
                .filter(this::shouldIncludeUser)
                .toList();

        List<User> newUsers = users.stream()
                .filter(user -> "first".equalsIgnoreCase(user.getLevel()))
                .collect(Collectors.toList());

        List<User> oldUsers = users.stream()
                .filter(user -> !"first".equalsIgnoreCase(user.getLevel()))
                .collect(Collectors.toList());

        Comparator<User> newStudentComparator = Comparator
                .comparingDouble((User u) -> Optional.ofNullable(u.getTotalGradesHighSchool()).orElse(0.0F)) // Handle null grades
                .reversed()
                .thenComparing((User u) -> Optional.ofNullable(u.getDateOfBirth()).orElse(LocalDate.from(LocalDateTime.MIN)), Comparator.reverseOrder()) // Handle null birth date
                .thenComparing((User u) -> governorateDistances.getOrDefault(u.getPlaceOfBirth(), 0.0), Comparator.reverseOrder());

        Comparator<User> oldStudentComparator = Comparator
                .comparingInt((User u) -> getLevelDiff(u.getLevel()))
                .thenComparingDouble(u -> Optional.ofNullable(u.getPreviousAcademicYearGpa()).orElse(0.0)) // Handle null GPA
                .reversed()
                .thenComparing((User u) -> Optional.ofNullable(u.getDateOfBirth()).orElse(LocalDate.from(LocalDateTime.MIN)), Comparator.reverseOrder()) // Handle null birth date
                .thenComparing((User u) -> governorateDistances.getOrDefault(u.getPlaceOfBirth(), 0.0), Comparator.reverseOrder());

        newUsers.sort(newStudentComparator);
        oldUsers.sort(oldStudentComparator);

        List<StudentDto> newStudentDtos = newUsers.stream().map(StudentMapper::toDto).collect(Collectors.toList());
        List<StudentDto> oldStudentDtos = oldUsers.stream().map(StudentMapper::toDto).collect(Collectors.toList());

        StudentsGroupedResponseDTO groupedResponse = new StudentsGroupedResponseDTO(oldStudentDtos, newStudentDtos);
        return groupedResponse;
    }

    @Override
    public ResponseEntity<Object> getSortedApplicants() {
        StudentsGroupedResponseDTO grouped = getSortedApplicantsData();
        BaseResponse response = new BaseResponse(true, "Applicants sorted successfully", grouped);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private boolean shouldIncludeUser(User user) {
        String address = Optional.ofNullable(user.getResidenceAddress()).orElse("").toLowerCase();

        if (address.contains("الواحات") || address.contains("el wahat")) return true;
        if (address.contains("كفر شكر") || address.contains("kafr shukr")) return true;

        if (address.contains("الجيزة") || address.contains("giza")) return false;
        if (address.contains("القاهرة") || address.contains("cairo")) return false;
        if (address.contains("القليوبية") || address.contains("qalyubia")) return false;

        return true;
    }

    private int getLevelDiff(String level) {
        return switch (level.toLowerCase()) {
            case "first" -> 1;
            case "second" -> 2;
            case "third" -> 3;
            case "fourth" -> 4;
            case "fifth" -> 5;
            case "sixth" -> 6;
            case "seventh" -> 7;
            default -> 0;
        };
    }

}

