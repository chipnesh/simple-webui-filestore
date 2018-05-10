package me.chipnesh.webui.handlers

import io.ktor.content.files
import io.ktor.content.static
import io.ktor.content.staticRootFolder
import io.ktor.routing.Routing
import java.io.File

fun Routing.static(uploadDir: File) {
    static("/files") {
        staticRootFolder = uploadDir.parentFile
        files(uploadDir.name)
    }
}