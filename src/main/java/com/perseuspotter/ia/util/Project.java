package com.perseuspotter.ia.util;

import com.perseuspotter.ia.scan.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;

public class Project implements Serializable {

  public Project(String path) throws IOException {
    this(path, true);
  }

  public Project(String path, boolean isSl) throws IOException {
    this.path = path;
    this.isSl = isSl;
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

  private static Logger logger = new Logger("Project");

  private String path;
  private boolean isSl;
  private boolean loaded;
  private Repository repo;
  private boolean hasRepo;
  private int[] forcedReqs;
  private int numReqs = -1;

  public String getPath() {
    return this.path;
  }

  public boolean isSl() {
    return this.isSl;
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

  public boolean doScanReq(int i) {
    return this.forcedReqs[i] == 0;
  }

  public boolean isForceOff(int i) {
    return this.forcedReqs[i] == 2;
  }

  public boolean isForceOn(int i) {
    return this.forcedReqs[i] == 1;
  }

  public void setForceOff(int i) {
    this.forcedReqs[i] = 2;
  }

  public void setForceOn(int i) {
    this.forcedReqs[i] = 1;
  }

  public void clearForceReq(int i) {
    this.forcedReqs[i] = 0;
  }

  public int getNumReqsCompleted() {
    return this.numReqs;
  }

  public void setNumReqsCompleted(int n) {
    this.numReqs = n;
  }

  public void load() throws IOException {
    File dir = new File(this.path + "/.ia");
    if (dir.isFile()) throw new IOException("found file, expected directory");
    this.forcedReqs = new int[Scanner.slReq.length + Scanner.hlReq.length];
    if (dir.exists()) {
      List<String> lines = Files.readAllLines(Paths.get(this.path + "/.ia"));
      this.isSl = Boolean.parseBoolean(lines.get(0));
      for (int i = 0; i < Scanner.slReq.length + Scanner.hlReq.length; i++) {
        this.forcedReqs[i] = Integer.parseInt(lines.get(1).substring(i, i + 1));
      }
      this.numReqs = Integer.parseInt(lines.get(2));
    } else {
      this.save();
    }
    File git = new File(this.path + "/.git");
    this.hasRepo = git.exists();
    if (this.hasRepo) this.repo = new FileRepository(git);
    this.loaded = true;
  }

  public void save() throws IOException {
    PrintWriter writer = new PrintWriter(this.path + "/.ia", "UTF-8");
    writer.println(this.isSl);
    for (int r : this.forcedReqs) writer.print(Integer.toString(r));
    writer.println();
    writer.println(this.numReqs);
    writer.close();
  }
}
