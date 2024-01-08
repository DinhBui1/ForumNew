package com.example.studentforum.Authetication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class RoleAdmin {
    public static List<GrantedAuthority> getRoleAdmin(){
        List<GrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority("ADMIN"));
        return  role;
    }
}
