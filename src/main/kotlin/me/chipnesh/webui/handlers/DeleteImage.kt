package me.chipnesh.webui.handlers

import io.ktor.application.call
import io.ktor.locations.get
import io.ktor.routing.Routing
import me.chipnesh.webui.IndexPage
import me.chipnesh.webui.DeleteFile
import me.chipnesh.webui.redirect
import java.io.File

fun Routing.deleteFile(uploadDir: File) {
    get<DeleteFile> {
        call.parameters["fileName"]?.let { name ->
            uploadDir.walkTopDown().drop(1).filter { it.name == name }
                    .forEach { it.delete() }
        }
        call.redirect(IndexPage())
    }
}