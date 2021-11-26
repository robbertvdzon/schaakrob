package com.vdzon.java

import com.vdzon.java.robitapi.RobotAansturing
import com.vdzon.java.schaakspel.Schaakspel
import io.javalin.Javalin
import io.javalin.http.Context

class RestEndpoints {
    private var robotAansturing: RobotAansturing? = null

    fun initRestEndpoints(app: Javalin?, robotAansturing: RobotAansturing, schaakspel: Schaakspel) {
        this.robotAansturing = robotAansturing
        app!!.post("/api/move") { ctx: Context -> move(ctx.body()) }
        app.post("/api/movevlak") { ctx: Context ->
            val (vlak, arm) = ctx.body().split(" ")
            robotAansturing.movetoVlak(vlak, arm.toInt())
        }
        app["/api/game/load", { ctx: Context? -> ctx?.json(schaakspel.load()) }]
        app["/api/game/reset", { ctx: Context? -> ctx?.json(schaakspel.reset()) }]
        app["/api/game/computermove", { ctx: Context? -> ctx?.json(schaakspel.computermove()) }]
        app.get("/api/game/ownmove/:van/:naar", { ctx: Context? -> ctx?.json(schaakspel.ownmove(ctx?.pathParam("van")?:"",ctx?.pathParam("naar")?:"")) })

        app["/api/rebuild", { ctx: Context? -> robotAansturing.rebuild() }]
        app["/api/restart", { ctx: Context? -> robotAansturing.restart() }]
        app["/api/home_vert", { ctx: Context? -> robotAansturing.homeVert() }]
        app["/api/home_hor", { ctx: Context? -> robotAansturing.homeHor() }]
        app["/api/sleep", { ctx: Context? -> robotAansturing.sleep() }]
        app["/api/clamp1", { ctx: Context? -> robotAansturing.clamp1() }]
        app["/api/release1", { ctx: Context? -> robotAansturing.release1() }]
        app["/api/clamp2", { ctx: Context? -> robotAansturing.clamp2() }]
        app["/api/release2", { ctx: Context? -> robotAansturing.release2() }]
        app["/api/bootsound", { ctx: Context? -> robotAansturing.bootsound() }]
        app["/api/hold", { ctx: Context? -> robotAansturing.hold() }]
        app["/api/drop", { ctx: Context? -> robotAansturing.drop() }]
        app["/api/activate", { ctx: Context? -> robotAansturing.activate() }]
        app["/api/deactivate", { ctx: Context? -> robotAansturing.deactivate() }]
        app["/api/a8", { ctx: Context -> ctx.result(robotAansturing.getA8()!!) }]
        app["/api/a11", { ctx: Context -> ctx.result(robotAansturing.getA11()!!) }]
        app["/api/a21", { ctx: Context -> ctx.result(robotAansturing.getA21()!!) }]
        app.post("/api/a8") { ctx: Context -> robotAansturing.setA8(ctx.body()) }
        app.post("/api/a11") { ctx: Context -> robotAansturing.setA11(ctx.body()) }
        app.post("/api/a21") { ctx: Context -> robotAansturing.setA21(ctx.body()) }

        app["/api/h1", { ctx: Context -> ctx.result(robotAansturing.getH1()!!) }]
        app["/api/h10", { ctx: Context -> ctx.result(robotAansturing.getH10()!!) }]
        app["/api/h20", { ctx: Context -> ctx.result(robotAansturing.getH20()!!) }]
        app.post("/api/h1") { ctx: Context -> robotAansturing.setH1(ctx.body()) }
        app.post("/api/h10") { ctx: Context -> robotAansturing.setH10(ctx.body()) }
        app.post("/api/h20") { ctx: Context -> robotAansturing.setH20(ctx.body()) }
        app["/api/snelheid", { ctx: Context -> ctx.result(robotAansturing.getSnelheid()!!) }]
        app.post("/api/snelheid") { ctx: Context -> robotAansturing.setSnelheid(ctx.body()) }
        app["/api/pakkerhoogte", { ctx: Context -> ctx.result(robotAansturing.getPakkerHoogte()!!) }]
        app.post("/api/pakkerhoogte") { ctx: Context -> robotAansturing.setPakkerHoogte(ctx.body()) }
        app["/api/delaynapak", { ctx: Context -> ctx.result(robotAansturing.getDelayNaPak()!!) }]
        app.post("/api/delaynapak") { ctx: Context -> robotAansturing.setDelayNaPak(ctx.body()) }
        app["/api/delaynazet", { ctx: Context -> ctx.result(robotAansturing.getDelayNaZet()!!) }]
        app.post("/api/delaynazet") { ctx: Context -> robotAansturing.setDelayNaZet(ctx.body()) }

        app["/api/demo", { ctx: Context -> ctx.result(robotAansturing.getDemoString()!!) }]
        app.post("/api/demo") { ctx: Context -> robotAansturing.setDemoString(ctx.body()) }
        app["/api/startdemoonce", { ctx: Context? -> robotAansturing.runDemoOnce() }]
        app["/api/startdemoloop", { ctx: Context? -> robotAansturing.runDemoLoop() }]
        app["/api/stopdemo", { ctx: Context? -> robotAansturing.stopDemo() }]
    }

    private fun move(body: String) {
        val split = body.split(",".toRegex()).toTypedArray()
        robotAansturing!!.moveto(Integer.valueOf(split[0]), Integer.valueOf(split[1]))
    }
}
