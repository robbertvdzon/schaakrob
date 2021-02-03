package com.vdzon.java.robitapi;


public interface RobotAansturing {

  void movetoVlak(String vlak);

  void moveto(int x, int y);

  void homeVert();

  void homeHor();

  void sleep();

  void clamp();

  void release();

  void rebuild();

  void restart();

  String getA8();

  void setA8(String pos);

  String getH1();

  void setH1(String pos);

  String getDemoString();

  void setDemoString(String demoString);

  void runDemoLoop();

  void runDemoOnce();

  void stopDemo();

}
