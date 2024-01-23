package com.perseuspotter.ia.scan;

import java.util.ArrayList;
import java.util.List;

public class Result {

  public static record Hit(String filePath, int line, int col) {}

  private String name;
  private String desc;
  private boolean isSl;
  private List<Hit> occurences;

  public Result(String name, String desc, boolean isSl) {
    this.name = name;
    this.desc = desc;
    this.isSl = isSl;
    occurences = new ArrayList<Hit>();
  }

  public String getName() {
    return name;
  }

  public String getDesc() {
    return desc;
  }

  public List<Hit> getOccurences() {
    return occurences;
  }

  public boolean isSl() {
    return isSl;
  }

  public void addOccurence(String path, int line, int col) {
    this.addOccurence(new Hit(path, line, col));
  }

  public void addOccurence(Hit h) {
    this.occurences.add(h);
  }
  // public boolean removeOccurence(int i);
}
