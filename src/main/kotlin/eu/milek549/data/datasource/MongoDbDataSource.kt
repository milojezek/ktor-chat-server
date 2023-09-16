package eu.milek549.data.datasource

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import eu.milek549.data.model.Message
import kotlinx.coroutines.flow.toList

/**
 * Implementation for MongoDB.
 * MongoDB will automatically create a collection of documents.
 */
class MongoDbDataSource(
    private val db: MongoDatabase
): MessageDataSource {

    private val messages = db.getCollection<Message>("messages")

    override suspend fun getAllMessages(): List<Message> {
        return messages.find().toList().sortedByDescending(Message::timestamp)
    }

    override suspend fun insertMessage(message: Message) {
        messages.insertOne(message)
    }
}