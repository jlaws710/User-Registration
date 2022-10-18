package com.Project1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * table for storing User information
 */
@Data
@Table
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @PrimaryKey
    @CassandraType(type = CassandraType.Name.TEXT)
    private String username;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String firstName;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String lastName;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String email;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String password;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String role;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String creditCard;
}
