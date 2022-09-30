package com.Project1.repository;

import com.Project1.entity.VerificationToken;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends CassandraRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
