package eu.milek549.room

import eu.milek549.data.datasource.MessageDataSource
import eu.milek549.data.model.Message
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

/**
 * RomController manages chat sessions,
 * makes sure it keeps all references to all the joined users
 * @param messageDataSource can be any data source
 *
 */
class RoomController(
    private val messageDataSource: MessageDataSource
) {
    // ConcurrentHasMap makes sure only one thread is running at the time
    private val members = ConcurrentHashMap<String, Member>()

    fun onJoin(
        userName: String,
        sessionId: String,
        socket: WebSocketSession
    ) {
        if (members.containsKey(userName)) {
            throw AlreadyInRoomException()
        }

        members[userName] = Member(
            userName = userName,
            sessionId = sessionId,
            socket = socket
        )
    }

    suspend fun sendMessage(sender: String, message: String) {
        members.values.forEach { member ->
            val messageEntity = Message(
                text = message,
                username = sender,
                timestamp = System.currentTimeMillis()
            )
            messageDataSource.insertMessage(messageEntity)
            // The message is saved in the database now

            val parsedMessage = Json.encodeToString(messageEntity)
            member.socket.send(Frame.Text(parsedMessage))
            // The message is sent now
        }
    }

    suspend fun getAllMessages(): List<Message> {
        return messageDataSource.getAllMessages()
    }

    suspend fun disconnect(username: String) {
        members[username]?.socket?.close()
        if (members.containsKey(username)) {
            members.remove(username)
        }
    }
}