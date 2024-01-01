package com.perseuspotter.ia.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;

public class Project implements Serializable {

  private static Logger logger = new Logger("Project");

  private boolean loaded;
  private String path;

  private Repository repo;
  private boolean hasRepo;

  public String getPath() {
    return this.path;
  }

  public boolean isLoaded() {
    return this.loaded;
  }

  public Repository getRepo() {
    return this.hasRepo ? this.repo : null;
  }

  public boolean hasRepo() {
    return this.hasRepo;
  }

  public Project(String path) throws IOException {
    this.path = path;
    File f = new File(path);
    if (!f.exists()) {
      logger.log("nothing at path " + path);
      throw new IOException("directory not found");
    }
    if (f.isFile()) {
      logger.log("expected directory at " + path);
      throw new IOException("found file, expected directory");
    }
  }

  public void load() throws IOException {
    File dir = new File(this.path + "/.ia");
    if (dir.isFile()) throw new IOException("found file, expected directory");
    if (dir.exists()) {
      // load shit
    } else {
      // load defaults
      this.save();
    }
    File git = new File(this.path + "/.git");
    this.hasRepo = git.exists();
    if (this.hasRepo) this.repo = new FileRepository(git);
    this.loaded = true;
  }

  public void save() {}
}
