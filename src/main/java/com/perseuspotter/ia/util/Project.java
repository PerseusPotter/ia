package com.perseuspotter.ia.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;

public class Project implements Serializable {

  private static Logger logger = new Logger("Project");

  public String path;

  public Project(String path) throws FileNotFoundException {
    this.path = path;
    File f = new File(path);
    if (!f.exists()) {
      logger.log("nothing at path " + path);
      throw new FileNotFoundException("directory not found");
    }
    if (f.isFile()) {
      logger.log("expected directory at " + path);
      throw new FileNotFoundException("found file, expected directory");
    }
  }

  public void load() {
    File dir = new File(this.path + "/.ia");
    if (!dir.exists()) return;
    if (dir.isFile()) return;
  }

  public void save() {}
}
