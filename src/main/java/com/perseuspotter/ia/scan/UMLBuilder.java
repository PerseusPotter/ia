package com.perseuspotter.ia.scan;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.perseuspotter.ia.util.Project;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.FileTreeIterator;

public class UMLBuilder {

  private Project proj;

  public UMLBuilder(Project proj) {
    this.proj = proj;
  }

  // https://yuml.me/diagram/scruffy/class/draw
  public String build() throws IOException {
    if (!this.proj.isLoaded()) this.proj.load();

    StringBuilder str = new StringBuilder();
    File[] files = this.getFiles();

    // trust
    for (File file : files) {
      CompilationUnit cu = StaticJavaParser.parse(file);
      cu
        .findAll(ClassOrInterfaceDeclaration.class, c -> c.isPublic())
        .forEach(c -> {
          str.append("[");
          c
            .getImplementedTypes()
            .forEach(v -> str.append("<<%s>>;".formatted(v.getNameAsString())));
          str.append(c.getNameAsString());
          str.append("|");
          c
            .findAll(FieldDeclaration.class)
            .forEach(f ->
              str.append(
                "%s%s: %s;".formatted(
                    f.isPublic() ? "+" : "-",
                    f.getVariable(0).getNameAsString(),
                    f.getCommonType()
                  )
              )
            );
          str.deleteCharAt(str.length() - 1);
          str.append("|");
          c
            .findAll(MethodDeclaration.class, m -> m.isPublic())
            .forEach(m -> {
              str.append(m.getNameAsString());
              str.append("(");
              m
                .getParameters()
                .forEach(p ->
                  str.append(
                    "%s: %s, ".formatted(
                        p.getNameAsString(),
                        p.getTypeAsString()
                      )
                  )
                );
              if (m.getParameters().size() > 0) str.delete(
                str.length() - 2,
                str.length()
              );
              str.append(");");
            });
          str.deleteCharAt(str.length() - 1);
          str.append("]\n");

          c
            .getExtendedTypes()
            .forEach(v ->
              str.append(
                "[%s]^[%s]\n".formatted(
                    v.getNameAsString(),
                    c.getNameAsString()
                  )
              )
            );
        });
    }

    return str.toString();
  }

  public File[] getFiles() throws IOException {
    if (this.proj.hasRepo()) {
      Repository repo = this.proj.getRepo();
      FileTreeIterator tree = new FileTreeIterator(repo);
      Set<File> files = new HashSet<File>();
      while (!tree.eof()) {
        files.add(tree.getEntryFile());
      }
      return files.toArray(new File[files.size()]);
    } else {
      return new File(this.proj.getPath())
        .listFiles(f -> f.isFile() && f.getName().endsWith(".java"));
    }
  }
}
