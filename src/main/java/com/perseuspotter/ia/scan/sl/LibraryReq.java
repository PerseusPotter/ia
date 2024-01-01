package com.perseuspotter.ia.scan.sl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.perseuspotter.ia.scan.Requirement;
import com.perseuspotter.ia.scan.Result;

public class LibraryReq extends Requirement {

  public Result init() {
    return new Result(
      "Use of Additional Libraries",
      "use a library ! (not language packages)"
    );
  }

  public void scan(String fp, CompilationUnit cu, Result res) {
    cu
      .findAll(
        ImportDeclaration.class,
        m -> !m.getNameAsString().startsWith("java")
      )
      .forEach(m -> this.addRes(fp, res, m));
  }
}
