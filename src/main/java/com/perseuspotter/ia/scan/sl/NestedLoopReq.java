package com.perseuspotter.ia.scan.sl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.perseuspotter.ia.scan.Requirement;
import com.perseuspotter.ia.scan.Result;

public class NestedLoopReq extends Requirement {

  public Result init() {
    return new Result("Nested Loop", "use a nested loop");
  }

  public void scan(String fp, CompilationUnit cu, Result res) {
    cu
      .findAll(
        ForStmt.class,
        l ->
          !l.findFirst(ForStmt.class).isEmpty() ||
          !l.findFirst(ForEachStmt.class).isEmpty() ||
          !l.findFirst(WhileStmt.class).isEmpty() ||
          !l.findFirst(DoStmt.class).isEmpty()
      )
      .forEach(l -> this.addRes(fp, res, l));
    cu
      .findAll(
        ForEachStmt.class,
        l ->
          !l.findFirst(ForStmt.class).isEmpty() ||
          !l.findFirst(ForEachStmt.class).isEmpty() ||
          !l.findFirst(WhileStmt.class).isEmpty() ||
          !l.findFirst(DoStmt.class).isEmpty()
      )
      .forEach(l -> this.addRes(fp, res, l));
    cu
      .findAll(
        WhileStmt.class,
        l ->
          !l.findFirst(ForStmt.class).isEmpty() ||
          !l.findFirst(ForEachStmt.class).isEmpty() ||
          !l.findFirst(WhileStmt.class).isEmpty() ||
          !l.findFirst(DoStmt.class).isEmpty()
      )
      .forEach(l -> this.addRes(fp, res, l));
    cu
      .findAll(
        DoStmt.class,
        l ->
          !l.findFirst(ForStmt.class).isEmpty() ||
          !l.findFirst(ForEachStmt.class).isEmpty() ||
          !l.findFirst(WhileStmt.class).isEmpty() ||
          !l.findFirst(DoStmt.class).isEmpty()
      )
      .forEach(l -> this.addRes(fp, res, l));
  }
}
