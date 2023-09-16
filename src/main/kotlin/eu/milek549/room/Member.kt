package eu.milek549.room

import io.ktor.websocket.*

/**
 * Member = user logged in the chat.
 * - sessionId = id of specific session the member is connected
 * - socket = used to send messages
 */
data class Member(
    val userName: String,
    val sessionId: String,
    val socket: WebSocketSession
)
