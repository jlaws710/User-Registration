package com.Project1.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

//import javax.persistence.*;

//@Entity
@Data
public class User {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKey
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    //@Column(length = 60)
    @Column
    private String password;

    private String role;
    private boolean enabled = false;

}
