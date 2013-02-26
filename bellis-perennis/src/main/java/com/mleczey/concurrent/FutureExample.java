package com.mleczey.concurrent;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FutureExample {
  private static final Logger logger = Logger.getLogger(FutureExample.class.getName());
  
  private ExecutorService service;
  
  public FutureExample() {
    this.service = Executors.newSingleThreadExecutor();
  }
  
  public static void main(String[] args) {
    try {
      FutureExample f = new FutureExample();
      f.run();
    } catch (MalformedURLException | ExecutionException | InterruptedException x) {
      logger.log(Level.SEVERE, "Error.", x);
    }
  }
  
  private void run() throws MalformedURLException, InterruptedException, ExecutionException {
    Future<String> future = this.service.submit(new Downloader("http://www.google.com"));
    while (!future.isDone()) {
      logger.log(Level.INFO, "Still waiting for task to finish...");
      Thread.sleep(1 * 1000);
    }
    logger.log(Level.INFO, "{0}", future.get());
    this.service.shutdown();
  }
}