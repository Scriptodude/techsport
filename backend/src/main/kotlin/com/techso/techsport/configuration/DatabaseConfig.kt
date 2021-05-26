package com.techso.techsport.configuration

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.techso.techsport.configuration.properties.TechsportProperties
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import java.util.concurrent.TimeUnit

@Configuration
class DatabaseConfig constructor(
    private val techsport: TechsportProperties
) : AbstractMongoClientConfiguration() {
    override fun getDatabaseName(): String {
        return this.techsport.database.name
    }

    override fun mongoClient(): MongoClient {
        val connString =
            ConnectionString("mongodb+srv://${techsport.database.user}:${techsport.database.password}@${techsport.database.host}/?retryWrites=true&w=majority")

        val mongoClientSettings = MongoClientSettings.builder()
            .applyToConnectionPoolSettings {
                it.maxConnectionIdleTime(10, TimeUnit.MINUTES)
                it.maxSize(3)
                it.minSize(1)
            }
            .applyToSocketSettings {
                it.connectTimeout(1, TimeUnit.MINUTES)
            }
            .applyConnectionString(connString)
            .build()

        return MongoClients.create(mongoClientSettings);
    }
}