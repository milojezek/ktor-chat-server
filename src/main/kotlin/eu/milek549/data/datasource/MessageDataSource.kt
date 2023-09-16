package eu.milek549.data.datasource

import eu.milek549.data.model.Message

/**
 * Interface that defines the functions needed to access the database.
 * Abstract interface, so it could be used for other kinds of DB.
 *
 * Suspend functions so we can only call them from within a coroutine
 * because the functions can be blocking.
 */
interface MessageDataSource {

    suspend fun getAllMessages(): List<Message>

    suspend fun insertMessage(message: Message)
}