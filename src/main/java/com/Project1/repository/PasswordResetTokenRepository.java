package com.Project1.repository;

import com.Project1.models.PasswordResetToken;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends CassandraRepository<PasswordResetToken, Integer> {
    @AllowFiltering
    PasswordResetToken findByToken(String token);
}
