package com.vdzon.java.ui

import com.vdzon.java.RestEndpoints
import com.vdzon.java.robitapi.RobotAansturing
import com.vdzon.java.robotclient.RobotAansturingClient
import com.vdzon.java.robotimpl.RobotAansturingImpl
import io.javalin.Javalin
import io.javalin.core.JavalinConfig
import io.javalin.plugin.rendering.vue.VueComponent

class MainWeb {
    var app: Javalin? = null
    fun start(schaakbord: Boolean) {
        println("Starting backend..")
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
            RobotAansturingClient("http://192.168.178.48:8080")
        }
        RestEndpoints().initRestEndpoints(app, robotAansturing)
        println("Starting server")
        robotAansturing.bootsound()
        app!!.start(8080)
    }

    fun stop() {
        app!!.stop()
    }
}
