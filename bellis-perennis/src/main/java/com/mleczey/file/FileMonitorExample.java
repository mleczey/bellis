package com.mleczey.file;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileMonitorExample {
  private static final Logger logger = Logger.getLogger(FileMonitorExample.class.getName());
  private static final String DIRECTORY = "c:/temp";
  
  public static void main(String[] args) {
    FileMonitorExample f = new FileMonitorExample();
    f.run();
  }
  
  private void run() {
    try {
      File directory = new File(DIRECTORY);
      FileAlterationObserver observer = new FileAlterationObserver(directory);
      observer.addListener(new FileAlternationListnerImpl());
      final FileAlterationMonitor monitor = new FileAlterationMonitor();
      monitor.addObserver(observer);
      monitor.start();
      
      Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            monitor.stop();
          } catch (Exception x) {
            logger.log(Level.SEVERE, "Error while stopping monitor.", x);
          }
        }
      }));
    } catch (Exception x) {
      logger.log(Level.SEVERE, "Error while monitoring directory " + DIRECTORY, x);
    }
  }

  private static class FileAlternationListnerImpl implements FileAlterationListener {
    @Override
    public void onStart(FileAlterationObserver observer) {
      logger.log(Level.INFO, "onStart {0}", observer.getDirectory());
    }

    @Override
    public void onDirectoryCreate(File directory) {
      logger.log(Level.INFO, "onDirectoryCreate {0}", directory);
    }

    @Override
    public void onDirectoryChange(File directory) {
      logger.log(Level.INFO, "onDirectoryChange {0}", directory);
    }

    @Override
    public void onDirectoryDelete(File directory) {
      logger.log(Level.INFO, "onDirectoryDelete {0}", directory);
    }

    @Override
    public void onFileCreate(File file) {
      logger.log(Level.INFO, "onFileCreate {0}", file);
    }

    @Override
    public void onFileChange(File file) {
      logger.log(Level.INFO, "onFileChange {0}", file);
    }

    @Override
    public void onFileDelete(File file) {
      logger.log(Level.INFO, "onFileDelete {0}", file);
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
      logger.log(Level.INFO, "onStop {0}", observer.getDirectory());
    }
  }
}
