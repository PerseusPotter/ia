package com.perseuspotter.ia.scan.sl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.perseuspotter.ia.scan.Requirement;
import com.perseuspotter.ia.scan.Result;

public class LoopReq extends Requirement {

  public Result init() {
    return new Result("Loop", "use a loop");
  }

  public void scan(String fp, CompilationUnit cu, Result res) {
    cu.findAll(ForStmt.class).forEach(l -> this.addRes(fp, res, l));
    cu.findAll(ForEachStmt.class).forEach(l -> this.addRes(fp, res, l));
    cu.findAll(WhileStmt.class).forEach(l -> this.addRes(fp, res, l));
    cu.findAll(DoStmt.class).forEach(l -> this.addRes(fp, res, l));
  }
}
