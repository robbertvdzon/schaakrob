package com.vdzon.java;

import com.vdzon.java.robitapi.RobotAansturing;
import io.javalin.Javalin;

public class RestEndpoints {

  private RobotAansturing robotAansturing;

  public void initRestEndpoints(Javalin app, RobotAansturing robotAansturing) {
    this.robotAansturing = robotAansturing;
    app.post("/api/move", ctx -> move(ctx.body()));
    app.post("/api/movevlak", ctx -> robotAansturing.movetoVlak(ctx.body()));
    app.get("/api/rebuild", ctx -> robotAansturing.rebuild());
    app.get("/api/restart", ctx -> robotAansturing.restart());
    app.get("/api/home_vert", ctx -> robotAansturing.homeVert());
    app.get("/api/home_hor", ctx -> robotAansturing.homeHor());
    app.get("/api/sleep", ctx -> robotAansturing.sleep());
    app.get("/api/clamp", ctx -> robotAansturing.clamp());
    app.get("/api/release", ctx -> robotAansturing.release());
    app.get("/api/a8", ctx -> ctx.result(robotAansturing.getA8()));
    app.post("/api/a8", ctx -> robotAansturing.setA8(ctx.body()));
    app.get("/api/h1", ctx -> ctx.result(robotAansturing.getH1()));
    app.post("/api/h1", ctx -> robotAansturing.setH1(ctx.body()));
    app.get("/api/demo", ctx -> ctx.result(robotAansturing.getDemoString()));
    app.post("/api/demo", ctx -> robotAansturing.setDemoString(ctx.body()));
    app.get("/api/startdemoonce", ctx -> robotAansturing.runDemoOnce());
    app.get("/api/startdemoloop", ctx -> robotAansturing.runDemoLoop());
    app.get("/api/stopdemo", ctx -> robotAansturing.stopDemo());
  }

  private void move(String body) {
    String[] split = body.split(",");
    robotAansturing.moveto(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
  }


}
