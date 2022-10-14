package com.Project1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String matchingPassword;
}
