package com.Project1.repository;

import com.Project1.models.User;
import java.util.Optional;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CassandraRepository<User, String> {
    User findByEmail(String email);

    void deleteByUsername(String username);

    Optional<User> findByUsername(String userName);
}
