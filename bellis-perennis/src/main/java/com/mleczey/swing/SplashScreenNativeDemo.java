package com.mleczey.swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Application cannot create and instance of SplashScreen class. Only a single
 * created instance can exist and can be obtained using getSplashScreen().
 * SplashScreen object is used to close splash screen, change the splash-screen
 * image, obtain the image position or size and paint in the splash screen.
 * 
 * Native splash screen can be displayed in the following ways:
 * - command line argument
 *   java -splash:<file name> <class name>
 * - jar with specified manifest option
 *  Manifest-Version 1.0
 *  Main-Class: <class name>
 *  SplashScreen-Image: <image name>
 *  Creating jar: jar cmf <mainfest file> <jar file> <class name> <image name>
 */

public class SplashScreenNativeDemo {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          JFrame frame = new SplashScreenNativeFrame();
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.setVisible(true);
        } catch (InterruptedException x) {
          Logger.getAnonymousLogger().log(Level.INFO, "Error", x);
        }
      }
    });
  }
}

class SplashScreenNativeFrame extends JFrame {
  private static final long serialVersionUID = 1L;
  
  public SplashScreenNativeFrame() throws InterruptedException {
    super.setName("Splash Screen");
    super.setSize(640, 480);
    this.initComponents();
  }
  
  private void initComponents() throws InterruptedException {
    super.setLayout(new BorderLayout());
    
    JMenuBar menuBar = new JMenuBar();
    super.setJMenuBar(menuBar);
    JMenu menu = new JMenu("File");
    menuBar.add(menu);
    JMenuItem menuItem = new JMenuItem("Exit");
    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    menu.add(menuItem);
    
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        e.getWindow().dispose();
      }
    });
    
    this.initSplashScreen();
  }
  
  private void initSplashScreen() throws InterruptedException {
    SplashScreen splashScreen = SplashScreen.getSplashScreen();
    if (null != splashScreen) {
      Graphics2D g2d = splashScreen.createGraphics();
      for (int i = 0; i < 100; i++) {
        this.renderSplashScreenFrame(g2d, i);
        splashScreen.update();
        TimeUnit.SECONDS.sleep(1);
      }
    } else {
      Logger.getAnonymousLogger().log(Level.INFO, "Splash screen not created.");
    }
  }
  
  private void renderSplashScreenFrame(Graphics2D g2d, int progress) {
    g2d.setComposite(AlphaComposite.Clear);
    g2d.fillRect(120, 140, 200, 40);
    g2d.setPaintMode();
    g2d.setColor(Color.BLACK);
    
    String[] components = {"images", "graphics", "content"};
    g2d.drawString("Loading " + components[(progress / 5)  % 3] + "...", 120, 150);
  }
}