package com.techso.techsport.configuration

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import java.util.concurrent.TimeUnit

@Configuration
class DatabaseConfig constructor(
    @Value("#{environment.DATABASE_HOST}")
    private val databaseHost: String,
    @Value("#{environment.DATABASE_USER}")
    private val databaseUser: String,
    @Value("#{environment.DATABASE_PASSWORD}")
    private val databasePwd: String,
    @Value("\${techsport.database.name}")
    val name: String
) : AbstractMongoClientConfiguration() {
    override fun getDatabaseName(): String {
        return this.name
    }

    override fun mongoClient(): MongoClient {
        val connString =
            ConnectionString("mongodb+srv://$databaseUser:$databasePwd@$databaseHost/?retryWrites=true&w=majority")

        val mongoClientSettings = MongoClientSettings.builder()
            .applyToConnectionPoolSettings {
                it.maxConnectionIdleTime(10, TimeUnit.MINUTES)
                it.maxSize(3)
                it.minSize(1)
            }
            .applyConnectionString(connString)
            .build()

        return MongoClients.create(mongoClientSettings);
    }
}