package me.chipnesh.webui

import io.ktor.client.HttpClient
import io.ktor.client.engine.config

val client = HttpClient(io.ktor.client.engine.cio.CIO.config {
    maxConnectionsCount = 1000 // Maximum number of socket connections.
    endpoint.apply {
        maxConnectionsPerRoute = 100 // Maximum number of requests for a specific endpoint route.
        pipelineMaxSize = 20 // Max number of opened endpoints.
        keepAliveTime = 5000 // Max number of milliseconds to keep each connection alive.
        connectTimeout = 5000 // Number of milliseconds to wait trying to connect to the server.
        connectRetryAttempts = 5 // Maximum number of attempts for retrying a connection.
    }
})