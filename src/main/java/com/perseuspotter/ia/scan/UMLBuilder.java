package com.perseuspotter.ia.scan;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ParserConfiguration.LanguageLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.perseuspotter.ia.util.Project;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

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
    ParserConfiguration config = new ParserConfiguration()
      .setLanguageLevel(LanguageLevel.JAVA_18);
    JavaParser parser = new JavaParser(config);
    for (File file : files) {
      CompilationUnit cu = parser.parse(file).getResult().get();
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
                    this.escape(f.getCommonType().asString())
                  )
              )
            );
          str.deleteCharAt(str.length() - 1);
          str.append("|");
          c
            .findAll(MethodDeclaration.class, m -> m.isPublic())
            .forEach(m -> {
              str.append(this.escape(m.getTypeAsString()));
              str.append(" ");
              str.append(m.getNameAsString());
              str.append("(");
              m
                .getParameters()
                .forEach(p ->
                  str.append(
                    "%s: %s, ".formatted(
                        p.getNameAsString(),
                        this.escape(p.getTypeAsString())
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

  private String escape(String str) {
    // return str.replace("[", "［").replace("]", "］");
    return str
      .replaceAll("(\\S+?)\\[\\]", "Array<$1>")
      .replace('<', '(')
      .replace('>', ')');
  }

  public File[] getFiles() throws IOException {
    if (this.proj.hasRepo()) {
      Repository repo = this.proj.getRepo();
      Set<File> files = new HashSet<File>();

      Ref head = repo.getRefDatabase().findRef("HEAD");
      RevWalk walk = new RevWalk(repo);

      RevCommit commit = walk.parseCommit(head.getObjectId());
      RevTree tree = commit.getTree();

      TreeWalk treeWalk = new TreeWalk(repo);
      treeWalk.addTree(tree);
      treeWalk.setRecursive(true);
      while (treeWalk.next()) {
        String p = treeWalk.getPathString();
        if (p.endsWith(".java")) files.add(new File(treeWalk.getPathString()));
      }

      walk.close();
      treeWalk.close();
      return files.toArray(new File[files.size()]);
    } else {
      return new File(this.proj.getPath())
        .listFiles(f -> f.isFile() && f.getName().endsWith(".java"));
    }
  }
}
