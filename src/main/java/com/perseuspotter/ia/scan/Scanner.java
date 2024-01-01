package com.perseuspotter.ia.scan;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.perseuspotter.ia.util.Project;
import java.io.File;
import java.io.IOException;

public class Scanner {

  private Project proj;

  public Scanner(Project p) {
    this.proj = p;
  }

  private Requirement[] slReq;

  {}

  private Requirement[] hlReq;

  {}

  public Result[] scan() throws IOException {
    String[] paths = getUpdatedFiles();
    Result[] r = new Result[slReq.length + hlReq.length];
    for (int i = 0; i < slReq.length; i++) r[i] = slReq[i].init();
    for (int i = 0; i < slReq.length; i++) r[i + slReq.length] =
      hlReq[i].init();

    for (int i = 0; i < paths.length; i++) {
      String p = proj.path + "/" + paths[i];
      CompilationUnit cu = StaticJavaParser.parse(new File(p));

      for (int ii = 0; ii < slReq.length; ii++) slReq[ii].scan(
          paths[i],
          cu,
          r[ii]
        );
      for (int ii = 0; ii < hlReq.length; ii++) hlReq[ii].scan(
          paths[i],
          cu,
          r[ii + slReq.length]
        );
    }

    return r;
  }

  private String[] getUpdatedFiles() {
    return new String[] { "yo" };
  }
}
