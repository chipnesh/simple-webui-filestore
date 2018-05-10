package me.chipnesh.webui

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.gson.GsonConverter
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Location
import io.ktor.locations.Locations
import io.ktor.routing.routing
import io.ktor.util.error
import kotlinx.html.*
import me.chipnesh.webui.handlers.*
import java.io.File
import java.io.IOException


fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Locations)
    install(PartialContent)
    install(ContentNegotiation) {
        register(ContentType.Application.Json, GsonConverter())
    }
    install(StatusPages) {
        exception<Throwable> { cause ->
            environment.log.error(cause)
            call.respondHtml {
                head {
                    styleLink(call.locationUrl(MainCss()))
                    title { +"File store" }
                    meta {
                        httpEquiv = HttpHeaders.ContentType
                        content = MetaHttpEquiv.contentType
                    }
                }
                body {
                    p { +"url: ${call.request.local.uri}" }
                    p { +"code: ${HttpStatusCode.InternalServerError}" }
                    p { +"cause: $cause" }
                }
            }
        }
    }
    routing(createUploadDir())
}

private fun Application.createUploadDir(): File {
    val config = environment.config.config("webui")
    val uploadDirPath: String = config.property("upload.dir").getString()
    val uploadDir = File(uploadDirPath)
    if (!uploadDir.mkdirs() && !uploadDir.exists()) {
        throw IOException("Failed to create directory ${uploadDir.absolutePath}")
    }
    return uploadDir
}

@Location("/") class IndexPage
@Location("/files") class Files
@Location("/files/.*?/delete") class DeleteFile(val fileName: String)
@Location("/files/upload") class UploadFile
@Location("/styles/main.css") class MainCss

private fun Application.routing(uploadDir: File) {
    routing {
        styles()
        static(uploadDir)
        indexPage(uploadDir)
        deleteFile(uploadDir)
        uploadFile(uploadDir)
    }
}


