package com.mleczey.util;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Timers schedules and execute TimerTask's. Method cancel on Timer, cancels whole timer, while on TimerTask cancels only particular task.
 * Canceling timer:
 * - timer won't cancel any current execution task
 * - timer will discard other scheduled tasks and will not execute them
 * - once task is finished, timer thread will terminate gracefully
 * - calling cancel more than once, will be ignored
 * 
 * Multiple TimerTask's can be scheduled on same Timer.
 * Tasks can be scheduled for executing it one or more time.
 * Timer will throw IllegalStateException if you try to schedule task on a canceled Timer or whose Task execution Thread has been terminated.
 */
public class TimerExample {
  private static final Logger logger = Logger.getLogger(TimerExample.class.getName());
  
  public static void main(String[] args) {
    TimerExample t = new TimerExample();
    t.run();
  }
  
  private void run() {
    Timer timer = new Timer();
    timer.schedule(new Reminder(timer), 1000);
  }
  
  private static class Reminder extends TimerTask {
    private Timer timer;
    
    private Reminder(Timer timer) {
      this.timer = timer;
    }
    
    @Override
    public void run() {
      logger.log(Level.INFO, "Remember to take your plis!");
      this.timer.cancel();
    }
  }
}
