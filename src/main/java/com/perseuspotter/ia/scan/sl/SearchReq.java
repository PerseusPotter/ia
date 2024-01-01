package com.perseuspotter.ia.scan.sl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.perseuspotter.ia.scan.Requirement;
import com.perseuspotter.ia.scan.Result;

public class SearchReq extends Requirement {

  public Result init() {
    return new Result("Searching", "search for something (idek man)");
  }

  public void scan(String fp, CompilationUnit cu, Result res) {
    // me? cope? never.
    cu
      .findAll(
        MethodCallExpr.class,
        m ->
          m.getNameAsString().equals("filter") ||
          m.getNameAsString().equals("select") ||
          m.getNameAsString().equals("indexOf")
      )
      .forEach(m -> this.addRes(fp, res, m));
  }
}
