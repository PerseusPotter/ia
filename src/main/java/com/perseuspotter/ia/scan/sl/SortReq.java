package com.perseuspotter.ia.scan.sl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.perseuspotter.ia.scan.Requirement;
import com.perseuspotter.ia.scan.Result;

public class SortReq extends Requirement {

  public Result init() {
    return new Result("Sorting", "sort an array", true);
  }

  public void scan(String fp, CompilationUnit cu, Result res) {
    // me? cope? never.
    cu
      .findAll(
        MethodCallExpr.class,
        m ->
          m.getNameAsString().equals("sort") &&
          !m.getTypeArguments().isEmpty() &&
          (
            m.getTypeArguments().get().get(0).isArrayType() ||
            m
              .getTypeArguments()
              .get()
              .get(0)
              .getMetaModel()
              .getTypeName()
              .equals("ArrayList") ||
            m
              .getTypeArguments()
              .get()
              .get(0)
              .getMetaModel()
              .getTypeName()
              .equals("List")
          )
      )
      .forEach(m -> this.addRes(fp, res, m));
  }
}
