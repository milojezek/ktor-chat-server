package eu.milek549.di

import com.mongodb.kotlin.client.coroutine.MongoClient
import eu.milek549.data.datasource.MessageDataSource
import eu.milek549.data.datasource.MongoDbDataSource
import eu.milek549.room.RoomController
import org.koin.dsl.module

val mainModule = module {

    // Database instance
    single {
        MongoClient.create()
            .getDatabase("message_db")
    }

    // Access to the database
    single<MessageDataSource> {
        MongoDbDataSource(get())
    }

    // Chat sessions manager
    single {
        RoomController(get())
    }
}