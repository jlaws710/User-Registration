package org.ProjectV1.repository

import org.ProjectV1.models.User
import org.springframework.data.cassandra.repository.AllowFiltering
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CassandraRepository<User, String> {
    @AllowFiltering
    fun findByEmail(email: String): User?

    @AllowFiltering
    fun findByUsername(userName: String): Optional<User>
}