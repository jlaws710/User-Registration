package com.Project1.repository;

import com.Project1.entity.User;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CassandraRepository<User, Integer> {
    @AllowFiltering
    User findByEmail(String email);
}
