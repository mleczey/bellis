package com.mleczey.apache.lang;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Five special characters in XML:
 * - & - &amp;
 * - < - &lt;
 * - > - &gt;
 * - " - &quot;
 * - ' - &apos;
 */
public class EscapeXMLExample {
  public static void main(String[] args) {
    EscapeXMLExample e = new EscapeXMLExample();
    e.run();
  }
  
  private void run() {
    String characters = "& < > \" '";
    Logger.getAnonymousLogger().log(Level.INFO, "{0}", StringEscapeUtils.ESCAPE_XML.translate(characters));
  }
}
