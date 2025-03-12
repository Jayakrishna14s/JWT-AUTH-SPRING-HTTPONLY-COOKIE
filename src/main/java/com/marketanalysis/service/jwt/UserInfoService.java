package com.marketanalysis.service.jwt;

import com.marketanalysis.dto.SignupDTO;
import com.marketanalysis.service.postgres.entity.User;
import com.marketanalysis.service.postgres.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
  import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = repository.findByUsername(username); // Assuming 'email' is used as username

        // Converting UserInfo to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public String addUser(SignupDTO payload) throws Exception{
        if(repository.findByUsername(payload.getUsername()).isPresent() ||
           repository.findByEmail(payload.getEmail()).isPresent()
        ){
            return "Not possible to add the user";
        } 

        User user = new User(
            payload.getEmail(), 
            payload.getUsername(), 
            encoder.encode(payload.getPassword())
        );
        repository.save(user);
        return "User Added Successfully";
    }
}