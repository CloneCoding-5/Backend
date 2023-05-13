package com.sparta.cloneproject_be.security;

import com.sparta.cloneproject_be.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private final User user; // 인증 완료된 User 객체
    private final String email; // 인증 완료된 User 의 email
    private final String password; // 인증 완료된 User 의 pw

    public UserDetailsImpl(User user, String email, String password) {
        this.user = user;
        this.email = email;
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
