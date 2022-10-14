package com.Project1.controller;

import com.Project1.event.RegistrationCompleteEvent;
import com.Project1.models.PasswordModel;
import com.Project1.models.User;
import com.Project1.models.UserModel;
import com.Project1.models.VerificationToken;
import com.Project1.repository.UserRepository;
import com.Project1.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/welcome")
    public String hello() {return "Welcome to my Project";
    }

    @GetMapping("/user/{username}")
    public User getUser(@PathVariable(value = "username") String username) {
        return userRepository.findByUsername(username);
    }

    @GetMapping("/admin")
    public List<User> list() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                applicationUrl(request)
        ));
        return "Success";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")) {
            return "User Successfully Verified";
        }
        return "Incorrect User";
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam ("token") String oldToken, HttpServletRequest request) {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        return "Verification Link re-sent";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        String url = "";
        if(user != null) {
            String token = UUID.randomUUID().toString();
            userService.createResetPasswordToken(user, token);
            url = passwordResetTokenMail(user, applicationUrl(request), token);
        }
        return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel) {
        String result = userService.validatePasswordResetToken(token);
        if(!result.equalsIgnoreCase("valid")) {
            return "Expired Token";
        }
        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if(user.isPresent()) {
            userService.changePassword(user.get(), passwordModel.getNewPassword());
            return "Password Reset Successfully";
        } else {
            return "Invalid Token";}
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        if (!userService.checkValidationOfPassword(user, passwordModel.getOldPassword())) {
            return "Invalid Old Password";
        }
        userService.changePassword(user, passwordModel.getNewPassword());
        return "Password has been changed";
    }
    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url =
                applicationUrl + "/savePassword?token="
                        + token;

        log.info("Click the link to reset your password: {}",
                url);
        return url;
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url =
                applicationUrl + "/verifyRegistration?token="
                        + verificationToken.getToken();

        log.info("Click the link to verify your account: {}",
                url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
