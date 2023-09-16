package eu.milek549

import eu.milek549.di.mainModule
import eu.milek549.plugins.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

/**
 * Entry point for the server
 */
fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
} // It will call the module below


/**
 * The functions are actually features of the server,
 * and they can be configured in the plugins' packages.
 */
fun Application.module() {
    install(Koin) {
        modules(mainModule)
    }

    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}
