package com.Project1.repository;

import com.Project1.entity.PasswordResetToken;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends CassandraRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
