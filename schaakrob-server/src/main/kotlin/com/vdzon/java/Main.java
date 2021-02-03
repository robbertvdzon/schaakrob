package com.vdzon.java;

import com.vdzon.java.ui.MainUI;
import com.vdzon.java.ui.MainWeb;

public class Main {
  public static void main(String[] args) {
    String OS = System.getProperty("os.name").toLowerCase();
    boolean schaakbord = !OS.contains("mac");

    if (schaakbord) {
      MainUI.main(args);
    }
    new MainWeb().start(schaakbord);
  }
}
