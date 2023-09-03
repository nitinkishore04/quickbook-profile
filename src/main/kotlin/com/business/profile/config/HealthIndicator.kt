package com.business.profile.config

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.Health
import org.springframework.stereotype.Component
import org.springframework.boot.actuate.health.HealthIndicator
import java.util.logging.Logger

@Component
class HealthIndicator : HealthIndicator  {

    @Value("\${spring.data.mongodb.uri}")
    private val mongoUri: String = "";

    override fun health(): Health {

        val mongoStatus = checkMongoConnection()
        return if(mongoStatus) {
            Health.up().build()
        } else {
            Health.down().withDetail("Error","Mongo Server is Down").build()
        }
    }

    fun checkMongoConnection() : Boolean {
        return try {
            val mongoClient = MongoClients.create(mongoUri)
            val dbName = "BusinessProfile"
            val database: MongoDatabase = mongoClient.getDatabase(dbName)
            val collectionList = database.listCollectionNames().toList();
            log?.info(collectionList.toString())
            collectionList.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        val log: Logger? = Logger.getLogger(HealthIndicator::class.java.name)
    }
}