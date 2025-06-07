package com.GP.GP.roles.user.service.impl;

import com.GP.GP.entities.User;
import com.GP.GP.roles.user.model.request.UserFilterDTO;
import com.GP.GP.security.Role;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<User> filterBy(UserFilterDTO dto, List<Role> excludedRoles) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (excludedRoles != null && !excludedRoles.isEmpty()) {
                predicates.add(cb.not(root.get("role").in(excludedRoles)));
            }

            if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
                predicates.add(root.get("status").in(dto.getStatus()));
            }

            if (dto.getSecurityCheck() != null && !dto.getSecurityCheck().isEmpty()) {
                predicates.add(root.get("securityCheck").in(dto.getSecurityCheck()));
            }

            if (dto.getHasPenalty() != null) {
                if (dto.getHasPenalty()) {
                    predicates.add(cb.isNotEmpty(root.get("penalties")));
                } else {
                    predicates.add(cb.or(
                            cb.isNull(root.get("penalties")),
                            cb.isEmpty(root.get("penalties"))
                    ));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
    // For filtering by user filter options (status, gender, etc.)
    public static Specification<User> filterByUserFilterDTO(UserFilterDTO dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
                predicates.add(root.get("status").in(dto.getStatus()));
            }

            if (dto.getSecurityCheck() != null && !dto.getSecurityCheck().isEmpty()) {
                predicates.add(root.get("securityCheck").in(dto.getSecurityCheck()));
            }

            if (dto.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), dto.getGender()));
            }

            if (dto.getHasPenalty() != null) {
                if (dto.getHasPenalty()) {
                    predicates.add(cb.greaterThan(cb.size(root.get("penalties")), 0));
                } else {
                    predicates.add(cb.equal(cb.size(root.get("penalties")), 0));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    // For including only specific roles (e.g., admins)
    public static Specification<User> includeOnlyRoles(List<Role> roles) {
        return (root, query, cb) -> {
            if (roles != null && !roles.isEmpty()) {
                return cb.and(root.get("role").in(roles));
            }
            return cb.conjunction();
        };
    }

}
