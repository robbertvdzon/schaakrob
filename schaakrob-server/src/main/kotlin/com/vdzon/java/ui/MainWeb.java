package com.vdzon.java.ui;

import com.vdzon.java.RestEndpoints;
import com.vdzon.java.robitapi.RobotAansturing;
import com.vdzon.java.robotclient.RobotAansturingClient;
import com.vdzon.java.robotimpl.RobotAansturingImpl;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.vue.VueComponent;

public class MainWeb {

  Javalin app;

  public void start(boolean schaakbord) {
    System.out.println("Starting backend..");

    app = Javalin.create(  (config) ->{
        config.enableWebjars();
      config.addStaticFiles("/html");
    });

    app.get("/", new VueComponent("<status></status>"));
    app.get("/demo", new VueComponent("<demo></demo>"));
    app.get("/status", new VueComponent("<status></status>"));
    app.get("/manual", new VueComponent("<manual></manual>"));
    app.get("/rebuild", new VueComponent("<rebuild></rebuild>"));

    RobotAansturing robotAansturing = null;
    if (schaakbord){
      robotAansturing = new RobotAansturingImpl();
    }
    else{
      robotAansturing = new RobotAansturingClient("http://192.168.178.42:8080");
    }

    new RestEndpoints().initRestEndpoints(app, robotAansturing);


    System.out.println("Starting server");
    app.start(8080);
  }


  public void stop() {
    app.stop();
  }


}
