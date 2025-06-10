package com.GP.GP.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AccessChecker {

    public boolean hasPrivilegeOrIsAdmin(Authentication authentication, String requiredPrivilege) {
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("❌ Not authenticated");
            return false;
        }


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("✅ Authenticated. Authorities: " + authorities);


        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        boolean hasPrivilege= authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals(requiredPrivilege));
        System.out.println("isAdmin: " + isAdmin + ", hasPrivilege: " + hasPrivilege);
        return isAdmin || hasPrivilege;
    }
}
