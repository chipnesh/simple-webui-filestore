package me.chipnesh.webui

import io.ktor.application.ApplicationCall
import io.ktor.application.feature
import io.ktor.locations.Locations
import io.ktor.request.host
import io.ktor.request.port
import io.ktor.response.respondRedirect


suspend fun ApplicationCall.redirect(location: Any) {
    respondRedirect(locationUrl(location))
}

fun ApplicationCall.locationUrl(location: Any) =
        "${getHostUrl()}${application.feature(Locations).href(location)}"

fun ApplicationCall.getHostUrl(): String {
    val host = request.host() ?: "localhost"
    val portSpec = request.port().let { if (it == 80) "" else ":$it" }
    return "http://$host$portSpec"
}