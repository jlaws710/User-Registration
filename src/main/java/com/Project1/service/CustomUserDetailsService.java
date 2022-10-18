package com.Project1.service;

import com.Project1.models.User;
import com.Project1.repository.UserRepository;
import java.util.Collections;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch User from the DB

        Optional<User> userRes = userRepository.findByUsername(username);
        // No user found
        if(userRes.isEmpty())
            throw new UsernameNotFoundException("Could not findUser with username = " + username);
        // Return a User Details object using the fetched User information
        User user = userRes.get();
        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }
}
