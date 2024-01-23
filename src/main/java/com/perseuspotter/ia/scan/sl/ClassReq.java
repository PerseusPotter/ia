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

public class ClassReq extends Requirement {

  public Result init() {
    return new Result("User Defined Object", "use a non-static class", true);
  }

  public void scan(String fp, CompilationUnit cu, Result res) {
    // find all classes
    cu
      .findAll(
        ClassOrInterfaceDeclaration.class,
        // if the class has a nonstatic + nonfinal property, or nonstatic method, it's a "user defined object"
        clazz ->
          !clazz
            .findFirst(
              FieldDeclaration.class,
              n -> !n.isStatic() && !n.isFinal()
            )
            .isEmpty() ||
          !clazz
            .findFirst(MethodDeclaration.class, n -> !n.isStatic())
            .isEmpty()
      )
      .forEach(clazz -> this.addRes(fp, res, clazz));
    cu
      .findAll(
        RecordDeclaration.class,
        // if the class has a nonstatic + nonfinal property, or nonstatic method, it's a "user defined object"
        clazz ->
          !clazz
            .findFirst(
              FieldDeclaration.class,
              n -> !n.isStatic() && !n.isFinal()
            )
            .isEmpty() ||
          !clazz
            .findFirst(MethodDeclaration.class, n -> !n.isStatic())
            .isEmpty()
      )
      .forEach(clazz -> this.addRes(fp, res, clazz));
    cu
      .findAll(
        LocalClassDeclarationStmt.class,
        // if the class has a nonstatic + nonfinal property, or nonstatic method, it's a "user defined object"
        clazz ->
          !clazz
            .findFirst(
              FieldDeclaration.class,
              n -> !n.isStatic() && !n.isFinal()
            )
            .isEmpty() ||
          !clazz
            .findFirst(MethodDeclaration.class, n -> !n.isStatic())
            .isEmpty()
      )
      .forEach(clazz -> this.addRes(fp, res, clazz));
    cu
      .findAll(
        LocalRecordDeclarationStmt.class,
        // if the class has a nonstatic + nonfinal property, or nonstatic method, it's a "user defined object"
        clazz ->
          !clazz
            .findFirst(
              FieldDeclaration.class,
              n -> !n.isStatic() && !n.isFinal()
            )
            .isEmpty() ||
          !clazz
            .findFirst(MethodDeclaration.class, n -> !n.isStatic())
            .isEmpty()
      )
      .forEach(clazz -> this.addRes(fp, res, clazz));
    cu
      .findAll(
        ClassOrInterfaceDeclaration.class,
        // if the class has a nonstatic + nonfinal property, or nonstatic method, it's a "user defined object"
        clazz ->
          !clazz
            .findFirst(
              FieldDeclaration.class,
              n -> !n.isStatic() && !n.isFinal()
            )
            .isEmpty() ||
          !clazz
            .findFirst(MethodDeclaration.class, n -> !n.isStatic())
            .isEmpty()
      )
      .forEach(clazz -> this.addRes(fp, res, clazz));
  }
}
