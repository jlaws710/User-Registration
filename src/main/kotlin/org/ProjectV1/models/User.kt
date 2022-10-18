package org.ProjectV1.models

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.cassandra.core.mapping.CassandraType
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
data class User (
    @PrimaryKey
    @CassandraType(type = CassandraType.Name.VARCHAR)
    val username: String,

    @CassandraType(type = CassandraType.Name.VARCHAR)
    val firstName: String,

    @CassandraType(type = CassandraType.Name.VARCHAR)
    val lastName: String,

    @CassandraType(type = CassandraType.Name.VARCHAR)
    val email: String,

    @CassandraType(type = CassandraType.Name.VARCHAR)
    var password: String,

    @CassandraType(type = CassandraType.Name.VARCHAR)
    var role: String? = null,

    @CassandraType(type = CassandraType.Name.VARCHAR)
    var creditCard: String? = null
)