package com.marketanalysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketanalysis.dto.LoginDTO;
import com.marketanalysis.dto.SignupDTO;
import com.marketanalysis.service.jwt.JwtService;
import com.marketanalysis.service.jwt.UserInfoService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserInfoService service; 

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupDTO payload) throws Exception {
        System.out.println("Initiating the user creation");
        return service.addUser(payload);
    }
 
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO payload, HttpServletResponse response) {
        System.out.println(payload.getUsername() + " " + payload.getPassword());
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword()));
        System.out.println("Yoyoyoyoy");
        if (authentication.isAuthenticated()) {
            System.out.println("Authenticated");
            // return jwtService.generateToken(payload.getUsername());
            String jwtToken = jwtService.generateToken(payload.getUsername());
            Cookie cookie = new Cookie("jwt", jwtToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);

            return "Login Sucessful";
        } else {
            System.out.println("Unauthenticated");
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully");
    }


}
