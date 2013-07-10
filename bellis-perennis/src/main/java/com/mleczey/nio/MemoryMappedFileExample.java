package com.mleczey.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoryMappedFileExample {
  private static final Logger logger = Logger.getLogger(MemoryMappedFileExample.class.getName());
  
  private static final String FILE_PATH = "./src/main/resources/nio.txt";
  private static final String STRING_TO_WRITE = "This is test to check writing.";
  
  public static void main(String[] args) {
    try {
      MemoryMappedFileExample m = new MemoryMappedFileExample();
      m.truncate();
      m.write();
      m.write();
      m.write();
      m.read();
    } catch (IOException x) {
      logger.log(Level.SEVERE, "Error while managing file.", x);
    }
  }
  
  private void truncate() throws IOException {
    new MemoryMappedFile() {
      @Override
      protected void doWork(FileChannel fileChannel) throws IOException {
        fileChannel.truncate(0);
      }
    }.execute();
  }
  
  private void write() throws IOException {
    new MemoryMappedFile() {
      @Override
      protected void doWork(FileChannel fileChannel) throws IOException {
        long position = fileChannel.size();
        byte[] bytes = (0 == position ? STRING_TO_WRITE : "\n" + STRING_TO_WRITE).getBytes(Charset.defaultCharset());
        MappedByteBuffer out = fileChannel.map(FileChannel.MapMode.READ_WRITE, position, bytes.length);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        out.put(byteBuffer);
      }
    }.execute();
  }
  
  private void read() throws IOException {
    new MemoryMappedFile() {
      @Override
      protected void doWork(FileChannel fileChannel) throws IOException {
        long size = fileChannel.size();
        MappedByteBuffer in = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, size);
        byte[] buffer = new byte[(int)size];
        in.get(buffer);
        
        logger.log(Level.INFO, "Read:\n{0}", new String(buffer, Charset.defaultCharset()));
      }
    }.execute();
  }
  
  private abstract class MemoryMappedFile {
    public void execute() throws IOException {  
      try (RandomAccessFile randomAccessFile = new RandomAccessFile(FILE_PATH, "rw");
          FileChannel fileChannel = randomAccessFile.getChannel();
          FileLock fileLock = fileChannel.lock();) {
        this.doWork(fileChannel);      
      }
    }
    
    protected abstract void doWork(FileChannel fileChannel) throws IOException;
  }
}
