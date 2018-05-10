package me.chipnesh.webui.handlers

import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.HttpHeaders
import io.ktor.locations.get
import io.ktor.locations.locations
import io.ktor.routing.Routing
import kotlinx.html.*
import me.chipnesh.webui.*
import java.io.File

fun Routing.indexPage(uploadDir: File) {
    get<IndexPage> {
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
                div {
                    p {
                        +"Загрузка файла"
                    }
                    form(locations.href(UploadFile()), encType = FormEncType.multipartFormData, method = FormMethod.post) {
                        acceptCharset = "utf-8"
                        fileInput { name = "file" }
                        submitInput { value = "отправить" }
                    }
                }
                div {
                    uploadDir.walkTopDown().drop(1).forEach {
                        val url = "${call.getHostUrl()}/files/${it.name}"
                        a(href = url) { img(src = url, alt = it.name) }
                        a(href = call.locationUrl(DeleteFile(it.name))) { +"x" }
                    }
                }
            }
        }
    }
}