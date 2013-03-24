package com.mleczey.basic;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

public class StringPaddingExample {
  private static final Logger logger = Logger.getLogger(StringPaddingExample.class.getName());
  
  public static void main(String[] args) {
    StringPaddingExample s = new StringPaddingExample();
    s.run();
  }
  
  private void run() {
    int i = 8;
    
    Class<ArrayList> c = ArrayList.class;
    logger.log(Level.INFO, "{0}", c.equals(List.class));
    
    logger.log(Level.INFO, "Left padded with zero: {0}", String.format("%03d", i));
    logger.log(Level.INFO, "Left padded with zero (Apache Commons): {0}", StringUtils.leftPad(Integer.toString(i), 3, "0"));
    logger.log(Level.INFO, "Left padded with zero (Guava): {0}", Strings.padStart(Integer.toString(i), 3, '0'));
    
    logger.log(Level.INFO, "Left padded with zero: #{0}#", String.format("%-3d", i));
    logger.log(Level.INFO, "Left padded with zero (Apache Commons): #{0}#", StringUtils.rightPad(Integer.toString(i), 3, "0"));
    logger.log(Level.INFO, "Left padded with zero (Guava): #{0}#", Strings.padEnd(Integer.toString(i), 3, '0'));
  }
}
