/*
 * Copyright (c) 2016 Richik Sinha Choudhury and Nick Dimitrov
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * A printer class for tests.
 */
public class TestOut implements AutoCloseable {

  public File file;
  private PrintWriter writer;

  public TestOut(String fileName) throws Exception {
    file = new File(fileName);
    if(file.isDirectory()) {
      throw new Exception("Filename points to directory, cannot write there.");
    }
    if(!file.isFile()) {
      file.createNewFile();
    }
    writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
  }

  public void println(Object obj) {
    String toWrite = obj.toString();
    System.out.println(toWrite);
    writer.println(toWrite);
  }

  public void print(Object obj) {
    String toWrite = obj.toString();
    System.out.print(toWrite);
    writer.print(toWrite);
  }

  public void close() {
    writer.close();
  }

}
