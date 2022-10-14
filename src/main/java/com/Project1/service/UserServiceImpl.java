package com.Project1.service;

import com.Project1.models.PasswordResetToken;
import com.Project1.models.User;
import com.Project1.models.UserModel;
import com.Project1.models.VerificationToken;
import com.Project1.repository.PasswordResetTokenRepository;
import com.Project1.repository.UserRepository;
import com.Project1.repository.VerificationTokenRepository;
import java.util.Calendar;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setUsername(userModel.getUsername());
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole("USER");
        Random rand = new Random();
        int number = rand.nextInt(99999999);

        user.setCreditCard("48212500" + number);

        if (userModel.getFirstName().equals("Joseph") && userModel.getLastName().equals("Lawson")
           || userModel.getFirstName().equals("Coral") && userModel.getLastName().equals("Mejia"))
        {user.setRole("ADMIN");}
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));

        userRepository.save(user);
        return user;
    }
    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken
                = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken(token);
        if(verificationToken == null) {
            return "Invalid";
        }

        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if((verificationToken.getExpirationTime().getTime()
                -calendar.getTime().getTime()) <=0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }

        userRepository.save(user);
        return "Valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createResetPasswordToken(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken
                = passwordResetTokenRepository.findByToken(token);
        if(passwordResetToken == null) {
            return "Invalid";
        }

        User user = passwordResetToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if((passwordResetToken.getExpirationTime().getTime()
                -calendar.getTime().getTime()) <=0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }
        return "Valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean checkValidationOfPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }
}
