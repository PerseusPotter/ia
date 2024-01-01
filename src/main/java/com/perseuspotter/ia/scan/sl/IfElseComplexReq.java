package com.perseuspotter.ia.scan.sl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.SwitchExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.perseuspotter.ia.scan.Requirement;
import com.perseuspotter.ia.scan.Result;

public class IfElseComplexReq extends Requirement {

  public Result init() {
    return new Result(
      "Complex Selection",
      "use of an if-else chain, or nested if, or switch"
    );
  }

  public void scan(String fp, CompilationUnit cu, Result res) {
    // find all ifs
    cu
      // if is a chain or has a nested if
      .findAll(
        IfStmt.class,
        stmt ->
          stmt.hasCascadingIfStmt() || !stmt.findFirst(IfStmt.class).isEmpty()
      )
      .forEach(stmt -> this.addRes(fp, res, stmt));
    cu.findAll(SwitchExpr.class).forEach(stmt -> this.addRes(fp, res, stmt));
    cu.findAll(SwitchStmt.class).forEach(stmt -> this.addRes(fp, res, stmt));
  }
}
