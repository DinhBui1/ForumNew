package com.example.studentforum.Authetication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class CheckRole {
    public static int checkRole(List<GrantedAuthority> authorities, List<GrantedAuthority> authority)
    {
        for (GrantedAuthority authority1 : authorities) {
            for (GrantedAuthority authority2 : authority) {
                if (authority1.getAuthority().equals(authority2.getAuthority())) {
                    return 1;
                }
            }
        }
        return 0;
    }
    public static List<GrantedAuthority> getRoleClient(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
        return  authorities;
    }

}
