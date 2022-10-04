package com.Project1.repository;

import com.Project1.entity.VerificationToken;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends CassandraRepository<VerificationToken, Integer> {
    @AllowFiltering
    VerificationToken findByToken(String token);
}
