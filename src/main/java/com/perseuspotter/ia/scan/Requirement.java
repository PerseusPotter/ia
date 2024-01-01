package com.perseuspotter.ia.scan;

import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

public abstract class Requirement {

  public abstract Result init();

  public abstract void scan(String filePath, CompilationUnit cu, Result res);

  public void addRes(String fp, Result r, Node n) {
    if (n.getBegin().isEmpty()) return;
    Position pos = n.getBegin().get();
    r.addOccurence(fp, pos.line, pos.column);
  }
}
