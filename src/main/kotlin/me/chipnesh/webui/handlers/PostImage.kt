package me.chipnesh.webui.handlers

import io.ktor.application.call
import io.ktor.content.PartData
import io.ktor.content.forEachPart
import io.ktor.locations.post
import io.ktor.request.receiveMultipart
import io.ktor.routing.Routing
import me.chipnesh.webui.IndexPage
import me.chipnesh.webui.redirect
import me.chipnesh.webui.UploadFile
import java.io.File

fun Routing.uploadFile(uploadDir: File) {
    post<UploadFile> {
        val multipart = call.receiveMultipart()
        multipart.forEachPart { part ->
            if (part is PartData.FileItem) {
                val ext = File(part.originalFileName).extension
                val file = File(uploadDir.absolutePath, "file-${System.currentTimeMillis()}.$ext")
                part.streamProvider().use { its -> file.outputStream().buffered().use { its.copyTo(it) } }
            }
            part.dispose()
        }
        call.redirect(IndexPage())
    }
}