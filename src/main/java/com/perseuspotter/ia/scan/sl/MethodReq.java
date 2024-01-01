package com.perseuspotter.ia.scan.sl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.perseuspotter.ia.scan.Requirement;
import com.perseuspotter.ia.scan.Result;

public class MethodReq extends Requirement {

  public Result init() {
    return new Result("User Defined Methods", "use a method");
  }

  public void scan(String fp, CompilationUnit cu, Result res) {
    cu.findAll(MethodDeclaration.class).forEach(m -> this.addRes(fp, res, m));
  }
}
