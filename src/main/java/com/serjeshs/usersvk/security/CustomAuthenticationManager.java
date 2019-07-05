package com.serjeshs.usersvk.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class CustomAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        Collection<GrantedAuthority> roles = Collections.emptyList();
        return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials(), roles);
    }
}