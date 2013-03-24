package com.mleczey.concurrent;

import com.google.common.base.Function;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * ExecutorCompletionService
 * advantages:
 * - processing responses immediately when they are ready
 * disadvantages:
 * - no counter, you must control on your own how many tasks have left
 * - it only works with Executor abstraction
 * - no built in support for processing incoming messages
 */
public class HandlingExecutorResponseExamples {
  private static final Logger logger = Logger.getLogger(HandlingExecutorResponseExamples.class.getName());
  private static final String[] DATA = {"http://www.google.com", "http://www.gmail.com", "http://news.google.com", "http://www.drive.google.com", "http://www.youtube.com"};
  
  public static void main(String[] args) {
    try {
      HandlingExecutorResponseExamples e = new HandlingExecutorResponseExamples();
      e.run();
    } catch (MalformedURLException | ExecutionException | InterruptedException x) {
      logger.log(Level.SEVERE, "Error.", x);
    }
  }
  
  private void run() throws MalformedURLException, InterruptedException, ExecutionException {
    this.waitingForFirstSubmitedTask();
    this.waitingForFirstCompletedTask();
    this.listeningForCompletedTask();
    this.advancedListanbleFuture();
  }

  private void waitingForFirstSubmitedTask() throws MalformedURLException, InterruptedException, ExecutionException {
    Map<String, Future<String>> futures = new TreeMap<>();
    
    ExecutorService service = Executors.newFixedThreadPool(2);
    
    for (String element : DATA) {
      futures.put(element, service.submit(new Downloader(element)));
    }
    
    for (Map.Entry<String, Future<String>> entry : futures.entrySet()) {
      String element = entry.getKey();
      logger.log(Level.INFO, "Checking task for {0}.", element);
      
      Future<String> future = entry.getValue();
      String s = future.get();
      if (!s.isEmpty()) {
        logger.log(Level.INFO, "For {0} task is done.", element);
      }
    }
    
    service.shutdown();
  }
  
  private void waitingForFirstCompletedTask() throws MalformedURLException, InterruptedException, ExecutionException {
    ExecutorService service = Executors.newFixedThreadPool(2);
    ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(service);
    
    for (String element : DATA) {
      completionService.submit(new Downloader(element));
    }
    
    // counter is needed, because ExecutorCompletionService does not provide any method to get number of compleated tasks.
    for (int i = 0; i < DATA.length; i++) {
      Future<String> future = completionService.take();
      String s = future.get();
      logger.log(Level.INFO, "Task for {0} completed.", getTitle(s));
    }
    
    service.shutdown();
  }
  
  private void listeningForCompletedTask() throws MalformedURLException {
    ExecutorService service = Executors.newFixedThreadPool(2);
    ListeningExecutorService listeningService = MoreExecutors.listeningDecorator(service);
    
    for (String element : DATA) {
      ListenableFuture<String> future = listeningService.submit(new Downloader(element));
      Futures.addCallback(future, new FutureCallback<String>() {
        @Override
        public void onSuccess(String result) {
          logger.log(Level.INFO, "Task for {0} completed.", getTitle(result));
        }

        @Override
        public void onFailure(Throwable x) {
          logger.log(Level.SEVERE, "Error.", x);
        }
      });
    }
    
    service.shutdown();
  }
  
  private static String getTitle(String s) {
    Pattern pattern = Pattern.compile("<title>(\\w+)</title>");
    Matcher matcher = pattern.matcher(s);
    return matcher.find() ? matcher.group(1) : StringUtils.EMPTY;
  }
  
  private void advancedListanbleFuture() throws MalformedURLException {
    this.taskReducing(this.taskMapping(this.taskChaining()));
  }
  
  private List<ListenableFuture<String>> taskChaining() throws MalformedURLException {
    logger.log(Level.INFO, "Task chaining...");
    ExecutorService service = Executors.newFixedThreadPool(2);
    final ListeningExecutorService listeningService = MoreExecutors.listeningDecorator(service);
    
    Function<String, String> parseFunction = new Function<String, String>() {
      @Override
      public String apply(String input) {
        logger.log(Level.INFO, "Parsing...");
        return input.substring(input.indexOf('.'), input.lastIndexOf('.'));
      }
    };
    
    AsyncFunction<String, String> addDecoration = new AsyncFunction<String, String>() {
      @Override
      public ListenableFuture<String> apply(String input) throws Exception {
        logger.log(Level.INFO, "Decorating...");
        SettableFuture<String> future = SettableFuture.create();
        future.set(String.format("###%s###", input));
        return future;
      }
    };
    
    List<ListenableFuture<String>> list = new ArrayList<>(10);
    for (final String element : DATA) {
      ListenableFuture<String> future = listeningService.submit(new Callable<String>() {
        @Override
        public String call() throws Exception {
          return element;
        }
      });
      ListenableFuture<String> parsedFuture = Futures.transform(future, parseFunction);
      ListenableFuture<String> addedFuture = Futures.transform(parsedFuture, addDecoration);
      list.add(addedFuture);
    }
    
    service.shutdown();
    
    return list;
  }
  
  private ListenableFuture<List<String>> taskMapping(List<ListenableFuture<String>> list) {
    logger.log(Level.INFO, "Task mapping...");
    ListenableFuture<List<String>> futures = Futures.allAsList(list);
    return futures;
  }
  
  private void taskReducing(ListenableFuture<List<String>> futures) {
    logger.log(Level.INFO, "Task reducing...");
    ListenableFuture<String> allHeadersFuture = Futures.transform(futures, new Function<List<String>, String>() {
      private StringBuilder sb = new StringBuilder("").append("\n");
      
      @Override
      public String apply(List<String> input) {
        for (String s : input) {
          this.sb.append(s).append("\n");
        }
        return this.sb.toString();
      }
    });
    
    Futures.addCallback(allHeadersFuture, new FutureCallback<String>() {
      @Override
      public void onSuccess(String result) {
        logger.log(Level.INFO, "Result {0}", result);
      }

      @Override
      public void onFailure(Throwable x) {
        logger.log(Level.SEVERE, "Error.", x);
      }
    });
  }
}
