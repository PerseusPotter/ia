package com.perseuspotter.ia.scan.sl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.perseuspotter.ia.scan.Requirement;
import com.perseuspotter.ia.scan.Result;

public class ArrayReq extends Requirement {

  public Result init() {
    return new Result("Array", "use an array", true);
  }

  public void scan(String fp, CompilationUnit cu, Result res) {
    cu
      .findAll(ArrayCreationExpr.class)
      .forEach(arr -> this.addRes(fp, res, arr));
  }
}
