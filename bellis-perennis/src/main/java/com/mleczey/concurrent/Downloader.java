package com.mleczey.concurrent;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import org.apache.commons.io.IOUtils;

class Downloader implements Callable<String> {
  private URL url;

  public Downloader(String address) throws MalformedURLException {
    this.url = new URL(address);
  }

  @Override
  public String call() throws Exception {
    String result = "";
    try (InputStream is = this.url.openStream()) {
      if (null != is) {
        result = IOUtils.toString(url, StandardCharsets.UTF_8);
      }
    }
    return result;
  }
}
