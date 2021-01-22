package com.techso.techsport.configuration

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration

@Configuration
class DatabaseConfig constructor(
    @Value("#{environment.DATABASE_HOST}")
    private val databaseHost: String,
    @Value("#{environment.DATABASE_USER}")
    private val databaseUser: String,
    @Value("#{environment.DATABASE_PASSWORD}")
    private val databasePwd: String
) : AbstractMongoClientConfiguration() {
    override fun getDatabaseName(): String {
        return "techsport"
    }

    override fun mongoClient(): MongoClient {
        val connString =
            ConnectionString("mongodb+srv://$databaseUser:$databasePwd@$databaseHost/techsports?retryWrites=true&w=majority")

        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connString)
            .build()

        return MongoClients.create(mongoClientSettings);
    }
}