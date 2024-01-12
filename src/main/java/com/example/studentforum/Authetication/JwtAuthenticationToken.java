package com.example.studentforum.Authetication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String userid;

    public JwtAuthenticationToken(UserDetails userDetails, String userid, Collection<? extends GrantedAuthority> authorities) {
        super(userDetails, null, authorities);
        this.userid = userid;
    }

    public String getUserid() {
        return this.userid;
    }
}