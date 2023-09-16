package eu.milek549.room

class AlreadyInRoomException: Exception(
    "There is already a member with this username in the room."
)