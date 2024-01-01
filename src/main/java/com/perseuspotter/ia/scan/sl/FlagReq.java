package com.perseuspotter.ia.scan.sl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.perseuspotter.ia.scan.Requirement;
import com.perseuspotter.ia.scan.Result;

public class FlagReq extends Requirement {

  public Result init() {
    return new Result(
      "Use of Constants/Flags",
      "use compile time constants or command line arguments"
    );
  }

  public void scan(String fp, CompilationUnit cu, Result res) {
    // me? cope? never.
    // TODO: make sure works
    cu
      .findAll(FieldDeclaration.class, f -> f.isFinal() && f.isStatic())
      .forEach(f -> this.addRes(fp, res, f));
    cu
      .findAll(
        FieldAccessExpr.class,
        f ->
          f.getNameAsExpression().isMethodCallExpr() &&
          f.getNameAsString().equals("getProperty") &&
          f.getScope().asNameExpr().getNameAsString().equals("System")
      )
      .forEach(f -> this.addRes(fp, res, f));
  }
}
