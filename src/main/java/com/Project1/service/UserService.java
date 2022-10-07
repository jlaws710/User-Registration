package com.Project1.service;

import com.Project1.models.User;
import com.Project1.models.UserModel;
import com.Project1.models.VerificationToken;
import java.util.Optional;

public interface UserService {
    User registerUser(UserModel userModel);
    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findUserByEmail(String email);

    void createResetPasswordToken(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkValidationOfPassword(User user, String oldPassword);
}
