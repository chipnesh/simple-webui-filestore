package me.chipnesh.webui.handlers

import io.ktor.application.call
import io.ktor.content.resolveResource
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import me.chipnesh.webui.MainCss

fun Route.styles() {
    get<MainCss> {
        call.respond(call.resolveResource("main.css")!!)
    }
}