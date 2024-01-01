package com.perseuspotter.ia.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.zip.GZIPOutputStream;

public class Logger {

  private String name;
  private static PrintWriter stream;

  {
    LocalDate d = LocalDate.now();
    String dateStr = String.format(
      "%04d-%02d-%02d",
      d.getYear(),
      d.getMonthValue(),
      d.getDayOfMonth()
    );

    new File("./logs").mkdir();

    int i = 1;
    String logFileName;

    do {
      logFileName = "./logs/" + dateStr + '-' + i + ".log.gz";
      i++;
    } while (new File(logFileName).exists());
    try {
      stream =
        new PrintWriter(
          new GZIPOutputStream(new FileOutputStream(logFileName), 8192)
        );
    } catch (IOException e) {
      System.out.println("unable to create log file");
      stream = new PrintWriter(new BufferedOutputStream(System.out));
    }
  }

  public Logger(String name) {
    this.name = name;
  }

  public enum Level {
    DEBUG,
    INFO,
    WARN,
    ERROR,
    FATAL,
  }

  public void log(Object obj) {
    log(obj.toString());
  }

  public void log(String str) {
    log(str, Level.INFO);
  }

  public void log(Object obj, Level lvl) {
    log(obj.toString(), lvl);
  }

  public void log(String str, Level lvl) {
    // if (lvl == Level.DEBUG) return;
    LocalTime d = LocalTime.now();
    stream.printf(
      "[%02d:%02d:%02d] [%s/%S]: %s%n",
      d.getHour(),
      d.getMinute(),
      d.getSecond(),
      this.name,
      lvl.toString(),
      str
    );
  }
}
