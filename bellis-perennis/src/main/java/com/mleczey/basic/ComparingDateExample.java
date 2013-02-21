package com.mleczey.basic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

public class ComparingDateExample {
  private static final Logger logger = Logger.getLogger(ComparingDateExample.class.getName());
  
  private Date from;
  private Date to;
  
  ComparingDateExample() throws ParseException {
    String sFrom = "10.10.2012 10:10:10";
    String sTo = "11.11.2013 11:11:11";
    
    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    
    this.from = format.parse(sFrom);
    this.to = format.parse(sTo);
  }
  
  public static void main(String[] args) {
    try {
      ComparingDateExample c = new ComparingDateExample();
      c.run();
    } catch (ParseException x) {
      logger.log(Level.SEVERE, "Error.", x);
    }
  }
  
  private void run() {
    long delta = this.to.getTime() - this.from.getTime();
    
    long days = (delta / (1000 * 60 * 60 * 24));
    long hours = (delta / (1000 * 60 * 60)) % 24;
    long minutes = (delta / (1000 * 60)) % 60;
    long seconds = (delta / 1000) % 60;
    
    logger.log(Level.INFO, "Difference betweend dates (Sun):{0} hours:{1} minutes:{2} seconds: {3}", new Object[]{days, hours, minutes, seconds});
    
    DateTime dt1 = new DateTime(this.from);
    DateTime dt2 = new DateTime(this.to);
    logger.log(Level.INFO, "Difference betweend dates (Joda):{0} hours:{1} minutes:{2} seconds: {3}", 
            new Object[]{
              Days.daysBetween(dt1, dt2).getDays(), 
              Hours.hoursBetween(dt1, dt2).getHours() % 24,
              Minutes.minutesBetween(dt1, dt2).getMinutes() % 60,
              Seconds.secondsBetween(dt1, dt2).getSeconds() % 60});
    
  }
}
