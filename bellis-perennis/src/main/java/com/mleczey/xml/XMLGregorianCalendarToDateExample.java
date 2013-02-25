package com.mleczey.xml;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * XML Schema has different data type for date, time and dateTime: xsd:date, xsd:time, xsd:dateTime.
 * It is better to use constructor instead of calling .getInstance(), because based on locale settings, it can return different callednar.
 */
public class XMLGregorianCalendarToDateExample {
  private static final Logger logger = Logger.getLogger(XMLGregorianCalendarToDateExample.class.getName());
  public static void main(String[] args) {
    XMLGregorianCalendarToDateExample x = new XMLGregorianCalendarToDateExample();
    x.run();
  }
  
  private void run() {
    Date now = new Date();
    
    XMLGregorianCalendar calendar = this.toXMLGregorianCalendar(now);
    logger.log(Level.INFO, "{0}", calendar);
    
    Date date = this.toDate(calendar);
    logger.log(Level.INFO, "{0}", date);
  }

  private XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
    XMLGregorianCalendar result = null;
    try {
      GregorianCalendar calendar = new GregorianCalendar();
      calendar.setTime(date);
      result = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
    } catch (DatatypeConfigurationException x) {
      logger.log(Level.SEVERE, "Error.", x);
    }
    return result;
  }
  
  private Date toDate(XMLGregorianCalendar calendar) {
    Date result = null;
    if (null != calendar) {
      result = calendar.toGregorianCalendar().getTime();
    }
    return result;
  }
}
