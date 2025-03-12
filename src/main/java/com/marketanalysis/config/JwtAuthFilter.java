package com.marketanalysis.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.marketanalysis.service.jwt.JwtService;
import com.marketanalysis.service.jwt.UserInfoService;
import java.io.IOException;
// import java.util.ArrayList;
import java.util.Arrays;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userDetailsService;



    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        String token = getJwtFromCookie(request);
        String username = null;

        if(token != null) {
            username = jwtService.extractUsername(token);
        }

        if (username != null ) {
            // String username = jwtService.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (SecurityContextHolder.getContext().getAuthentication() == null &&  jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        chain.doFilter(request, response);
    }

    private String getJwtFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    // @SuppressWarnings("null")
    // @Override
    // protected void doFilterInternal(HttpServletRequest request,
    //         HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    //     // Retrieve the Authorization header
    //     String authHeader = request.getHeader("Authorization");
    //     String token = null;
    //     String username = null;

    //     // Check if the header starts with "Bearer "
    //     if (authHeader != null && authHeader.startsWith("Bearer ")) {
    //         token = authHeader.substring(7); // Extract token
    //         username = jwtService.extractUsername(token); // Extract username from token
    //     }

    //     // If the token is valid and no authentication is set in the context
    //     if (username != null &&
    //             SecurityContextHolder.getContext().getAuthentication() == null) {
    //         UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    //         // Validate token and set authentication
    //         if (jwtService.validateToken(token, userDetails)) {
    //             UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
    //                     userDetails,
    //                     null,
    //                     userDetails.getAuthorities());
    //             authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    //             SecurityContextHolder.getContext().setAuthentication(authToken);
    //         }
    //     }

    //     // Continue the filter chain
    //     filterChain.doFilter(request, response);
    // }

}

    // protected void doFilterInternal(HttpServletRequest request,
    // HttpServletResponse response, FilterChain filterChain)
    // throws ServletException, IOException {
    // String token = null;
    // String username = null;

    // // Retrieve cookies from the request
    // Cookie[] cookies = request.getCookies();
    // if (cookies != null) {
    // for (Cookie cookie : cookies) {
    // if ("jwt".equals(cookie.getName())) {
    // token = cookie.getValue();
    // break;
    // }
    // }
    // }

    // // If a token is found, extract the username
    // if (token != null) {
    // username = jwtService.extractUsername(token);
    // }

    // // Proceed with authentication if username is present and context is not
    // // authenticated
    // if (username != null &&
    // SecurityContextHolder.getContext().getAuthentication() == null) {
    // UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    // // Validate token and set authentication
    // if (jwtService.validateToken(token, userDetails)) {
    // UsernamePasswordAuthenticationToken authToken = new
    // UsernamePasswordAuthenticationToken(
    // userDetails,
    // null,
    // userDetails.getAuthorities());
    // authToken.setDetails(new
    // WebAuthenticationDetailsSource().buildDetails(request));
    // SecurityContextHolder.getContext().setAuthentication(authToken);
    // }
    // }

    // // Continue the filter chain
    // filterChain.doFilter(request, response);
    // }

