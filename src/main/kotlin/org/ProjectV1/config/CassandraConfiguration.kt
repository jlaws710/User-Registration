package org.ProjectV1.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.config.SchemaAction

/**
 * properties for Cassandra Configuration
 */
@Configuration
class CassandraConfiguration : AbstractCassandraConfiguration() {
    @Value("\${spring.data.cassandra.keyspace-name}")
    private var keySpace: String = ""

    @Value("\${spring.data.cassandra.contact-points}")
    private var contactPoints: String = ""

    @Value("\${spring.data.cassandra.port}")
    private var port = 0

    @Value("{spring.data.cassandra.schema-action}")
    private var schemaAction: String = ""

    override fun getKeyspaceName(): String {
        return keySpace
    }

    public override fun getContactPoints(): String {
        return contactPoints
    }

    fun setContactPoints(contactPoints: String) {
        this.contactPoints = contactPoints
    }

    public override fun getPort(): Int {
        return port
    }

    fun setPort(port: Int) {
        this.port = port
    }

    override fun getSchemaAction(): SchemaAction {
        return SchemaAction.CREATE_IF_NOT_EXISTS
    }
}