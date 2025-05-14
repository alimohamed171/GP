package com.GP.GP.security;

import com.GP.GP.entities.User;
import com.GP.GP.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImp(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();


        // Add role (e.g., ROLE_ADMIN)
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        System.out.println("Adding role: " + user.getRole().name());


        // Add fine-grained privileges (e.g., CAN_EDIT_STUDENT)
        user.getPrivileges().forEach(priv -> {
            System.out.println("Adding privilege: {" + priv.getName()+"}");
            authorities.add(new SimpleGrantedAuthority(priv.getName())); }
        );

        return authorities;
    }


}
