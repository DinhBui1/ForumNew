package com.example.studentforum.Config;

import com.example.studentforum.Authetication.JwtAuthenticationToken;
import com.example.studentforum.Model.CustomAdminDetails;
import com.example.studentforum.Model.CustomUserDetails;
import com.example.studentforum.Model.User;
import com.example.studentforum.Service.TokenService;
import com.example.studentforum.Service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                Claims claims = tokenService.getClaimFromJWT(token);
                String sub = claims.getSubject();
                int roleid = (int) claims.get("roleid");
                User u = userService.getUserByid(sub);
                UserDetails userDetails = createUserDetails(u, roleid);

                if (sub != null) {
                    JwtAuthenticationToken auth = new JwtAuthenticationToken(userDetails, sub, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private UserDetails createUserDetails(User u, int roleid) {
        if (roleid == 2) {
            return new CustomUserDetails(u);
        } else {

            return new CustomAdminDetails(u);

        }
    }
}
