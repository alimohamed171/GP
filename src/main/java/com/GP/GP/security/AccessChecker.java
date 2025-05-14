package com.GP.GP.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AccessChecker {

    public boolean hasPrivilegeOrIsAdmin(Authentication authentication, String requiredPrivilege) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();


        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
        if (isAdmin) {
            return true;
        }


        return authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals(requiredPrivilege));
    }
}
