package com.perseuspotter.ia.scan.sl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.IfStmt;
import com.perseuspotter.ia.scan.Requirement;
import com.perseuspotter.ia.scan.Result;

public class IfElseReq extends Requirement {

  public Result init() {
    return new Result("Simple Selection", "use of an if-else statement", true);
  }

  public void scan(String fp, CompilationUnit cu, Result res) {
    // find all ifs
    cu
      .findAll(IfStmt.class, stmt -> stmt.hasElseBranch())
      .forEach(stmt -> this.addRes(fp, res, stmt));
  }
}
