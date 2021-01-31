package com.vdzon.schaakrob

import io.javalin.Javalin
import io.javalin.plugin.rendering.vue.VueComponent


class Main {

    fun start() {
        val app = Javalin.create { config ->
            config.enableWebjars()
            config.addStaticFiles("/html")
        }.start(7000)

        app.get("/", VueComponent("<status></status>"))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val main = Main()
            main.start()
        }
    }

}
