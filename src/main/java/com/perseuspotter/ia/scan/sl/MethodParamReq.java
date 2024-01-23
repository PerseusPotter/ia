package com.perseuspotter.ia.scan.sl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.perseuspotter.ia.scan.Requirement;
import com.perseuspotter.ia.scan.Result;

public class MethodParamReq extends Requirement {

  public Result init() {
    return new Result(
      "User Defined Methods with Parameters",
      "use a method with parameters, and use the parameter",
      true
    );
  }

  public void scan(String fp, CompilationUnit cu, Result res) {
    // TODO: MAY NOT WORK
    cu
      .findAll(
        MethodDeclaration.class,
        m -> m.getParameters().size() > 0 && m.isAncestorOf(m.getParameter(0))
      )
      .forEach(m -> this.addRes(fp, res, m));
  }
}
