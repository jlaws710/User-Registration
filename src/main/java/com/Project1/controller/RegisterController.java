package com.Project1.controller;

import com.Project1.models.*;
import com.Project1.repository.UserRepository;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private JWTUtil jwtUtil;

    @GetMapping("/user/{username}")
    public Optional<User> getUser(@PathVariable(value = "username") String username) {
        //return userRepository.findByUsername(username);
        return userRepository.findByUsername(username);
    }

    @GetMapping("/admin")
    public List<User> list() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody User user){
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        user.setRole("USER");
        Random rand = new Random();
        int number = rand.nextInt(99999999);

        user.setCreditCard("48212500" + number);

        if (user.getFirstName().equals("Joseph") && user.getLastName().equals("Lawson")
                || user.getFirstName().equals("Coral") && user.getLastName().equals("Mejia"))
        {user.setRole("ADMIN");}

        user = userRepository.save(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return Collections.singletonMap("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody User body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getUsername());

            return Collections.singletonMap("jwt-token", token);
        } catch (AuthenticationException authExc){
            throw new RuntimeException("Invalid Login Credentials");
        }
    }

    @PutMapping("/user/{username}")
    public ResponseEntity<Object> updateUserDetails(
            @RequestBody User userBody,
            @PathVariable String username) {

        Optional<Optional<User>> userRepo = Optional.ofNullable( (userRepository.findByUsername(username)));

        if (!userRepo.isPresent() )
            return ResponseEntity.notFound().build();

        userBody.setUsername(username);
        userRepository.save(userBody);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/{username}")
    public void deleteUser(@PathVariable(value = "username") String username) {
        userRepository.deleteByUsername(username);
    }
}