package com.mleczey.net;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OwnAddress {
  private static final Logger logger = Logger.getLogger(OwnAddress.class.getName());
  
  public static void main(String[] args) {
    OwnAddress address = new OwnAddress();
    address.run();
  }
  
  private void run() {
    try {
      InetAddress address = InetAddress.getLocalHost();
      logger.log(Level.INFO, "IPv4 address: {0}\nHostname: {1}", new Object[] {address.getHostAddress(), address.getHostName()});
    } catch (UnknownHostException x) {
      logger.log(Level.SEVERE, "Error", x);
    }
  }
}
