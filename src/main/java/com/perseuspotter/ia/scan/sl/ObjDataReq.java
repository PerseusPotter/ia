package com.perseuspotter.ia.scan.sl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.RecordDeclaration;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.LocalRecordDeclarationStmt;
import com.perseuspotter.ia.scan.Requirement;
import com.perseuspotter.ia.scan.Result;

public class ObjDataReq extends Requirement {

  public Result init() {
    return new Result(
      "Data Object",
      "use an object (class) as a data record",
      true
    );
  }

  // too much effort to scan across files to double check if it actually is accessed as a data record
  public void scan(String fp, CompilationUnit cu, Result res) {
    // find all classes
    cu
      // if has getter and setter and data field it is data record (not coping trust)
      .findAll(
        ClassOrInterfaceDeclaration.class,
        clazz ->
          !clazz
            .findFirst(FieldDeclaration.class, n -> !n.isStatic())
            .isEmpty() &&
          !clazz
            .findFirst(
              MethodDeclaration.class,
              n ->
                n.getNameAsString().startsWith("get") &&
                !n.getType().isVoidType()
            )
            .isEmpty() &&
          !clazz
            .findFirst(
              MethodDeclaration.class,
              n ->
                n.getNameAsString().startsWith("set") &&
                n.getType().isVoidType() &&
                n.getParameters().size() == 1
            )
            .isEmpty()
      )
      .forEach(clazz -> this.addRes(fp, res, clazz));
    // find all records
    cu
      .findAll(RecordDeclaration.class)
      .forEach(record -> this.addRes(fp, res, record));
    cu
      .findAll(LocalClassDeclarationStmt.class)
      .forEach(record -> this.addRes(fp, res, record));
    cu
      .findAll(LocalRecordDeclarationStmt.class)
      .forEach(record -> this.addRes(fp, res, record));
  }
}
