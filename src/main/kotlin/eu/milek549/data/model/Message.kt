package eu.milek549.data.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

/** Message
 * serializable = can be serialized with JSON
 * text         = text of the message
 * username     = who has sent the message
 * timestamp    = when the message was sent
 * id           = id used for identification of messages
 *
 * The message will be sent as a JSON string over the network.
 *
 * Used randomUUID because of problems with auto serialization.
 */
@Serializable
data class Message(
    val text: String,
    val username: String,
    val timestamp: Long,
    @SerialName("_id")
    @Contextual val id: String= generateId()
) {
    companion object {
        private const val PREFIX = "MES"

        private fun generateId(): String {
            return PREFIX + UUID.randomUUID().toString()
        }
    }
}
