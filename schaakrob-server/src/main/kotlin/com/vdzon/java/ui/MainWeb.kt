package com.vdzon.java.ui

import com.vdzon.java.RestEndpoints
import com.vdzon.java.robitapi.RobotAansturing
import com.vdzon.java.robotclient.RobotAansturingClient
import com.vdzon.java.robotimpl.RobotAansturingImpl
import io.javalin.Javalin
import io.javalin.core.JavalinConfig
import io.javalin.plugin.rendering.vue.JavalinVue
import io.javalin.plugin.rendering.vue.VueComponent
import org.slf4j.LoggerFactory
import java.nio.file.Path

class MainWeb {
    private val log = LoggerFactory.getLogger(MainWeb::class.java)

    var app: Javalin? = null
    fun start(schaakbord: Boolean) {
        log.info("Starting backend..")
        app = Javalin.create { config: JavalinConfig ->
            config.enableWebjars()
            config.addStaticFiles("/html")
        }
        app!!.get("/", VueComponent("<status></status>"))
        app!!.get("/demo", VueComponent("<demo></demo>"))
        app!!.get("/status", VueComponent("<status></status>"))
        app!!.get("/manual", VueComponent("<manual></manual>"))
        app!!.get("/rebuild", VueComponent("<rebuild></rebuild>"))
        var robotAansturing: RobotAansturing? = null
        robotAansturing = if (schaakbord) {
            RobotAansturingImpl()
        } else {
            RobotAansturingClient("http://192.168.178.50:8080")
        }
        RestEndpoints().initRestEndpoints(app, robotAansturing)
        log.info("Starting server")
        robotAansturing.bootsound()
        app!!.start(8080)
    }

    fun stop() {
        app!!.stop()
    }
}
