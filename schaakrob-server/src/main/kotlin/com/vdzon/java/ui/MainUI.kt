package com.vdzon.java.ui;


import javax.swing.*;

public class MainUI {
  public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        MyPanel mainPanel = new com.vdzon.java.ui.MyPanel();
        mainPanel.fullscreen();
      }

    });
  }


}
