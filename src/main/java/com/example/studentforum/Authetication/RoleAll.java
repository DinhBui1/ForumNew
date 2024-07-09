package com.example.studentforum.Authetication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class RoleAll {
    public static List<GrantedAuthority> getRoleAll() {
        List<GrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority("USER"));
        role.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        return role;
    }

    public static List<GrantedAuthority> getRoleUser() {
        List<GrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority("USER"));
        return role;
    }

    public static List<GrantedAuthority> getRoleAdmin() {
        List<GrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority("ADMIN"));
        return role;
    }

}
