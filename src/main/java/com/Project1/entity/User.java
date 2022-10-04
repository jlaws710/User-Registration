package com.Project1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @PrimaryKey
    private int id;

    private String firstName;
    private String lastName;
    private String email;

    //@Column(length = 60)
    @Column
    private String password;

    private String role;
    //private boolean enabled = false;

}
