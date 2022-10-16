package com.Project1.repository;

import com.Project1.models.User;
import java.util.Optional;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CassandraRepository<User, Integer> {
    @AllowFiltering
    User findByEmail(String email);

    @AllowFiltering
    //User findByUsername(String userName);
    Optional<User> findByUsername(String userName);
}
