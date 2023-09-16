package eu.milek549.routes

import eu.milek549.room.AlreadyInRoomException
import eu.milek549.room.RoomController
import eu.milek549.session.ChatSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

fun Route.chatSocket(roomController: RoomController) {
    webSocket(path = "/chat-socket") {
        val session = call.sessions.get<ChatSession>()
        if (session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session."))
            return@webSocket
        }

        try {
            roomController.onJoin(
                userName = session.username,
                sessionId = session.sessionId,
                socket = this
            )

            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    roomController.sendMessage(
                        sender = session.username,
                        message = frame.readText()
                    )
                }
            }
        } catch(e: AlreadyInRoomException) {
            call.respond(HttpStatusCode.Conflict)
        } catch(e: Exception) {
            e.printStackTrace()
        } finally {
            roomController.disconnect(session.username)
        }
    }
}

fun Route.getAllMessages(roomController: RoomController) {
    get("/messages") {
        call.respond(
            HttpStatusCode.OK,
            roomController.getAllMessages()
        )
    }
}