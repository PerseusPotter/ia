package com.perseuspotter.ia.scan;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ParserConfiguration.LanguageLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.perseuspotter.ia.scan.sl.*;
import com.perseuspotter.ia.util.Project;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import org.eclipse.jgit.lib.IndexDiff;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.FileTreeIterator;

public class Scanner {

  private Project proj;

  public Scanner(Project p) {
    this.proj = p;
  }

  private static Requirement[] slReq = new Requirement[] {
    new ArrayReq(),
    new ClassReq(),
    new ObjDataReq(),
    new IfElseReq(),
    new IfElseComplexReq(),
    new LoopReq(),
    new NestedLoopReq(),
    new MethodReq(),
    new MethodParamReq(),
    new MethodReturnReq(),
    new SortReq(),
    new SearchReq(),
    new FileReq(),
    new LibraryReq(),
    new FlagReq(),
  };

  private static Requirement[] hlReq;

  public Result[] scan() throws IOException {
    if (!this.proj.isLoaded()) this.proj.load();

    String[] paths = this.getChangedFiles();
    Result[] r = new Result[slReq.length + hlReq.length];
    for (int i = 0; i < slReq.length; i++) r[i] = slReq[i].init();
    for (int i = 0; i < hlReq.length; i++) r[i + slReq.length] =
      hlReq[i].init();

    ParserConfiguration config = new ParserConfiguration()
      .setLanguageLevel(LanguageLevel.JAVA_18);
    JavaParser parser = new JavaParser(config);
    for (int i = 0; i < paths.length; i++) {
      String p = proj.getPath() + "/" + paths[i];
      CompilationUnit cu = parser.parse(new File(p)).getResult().get();

      for (int ii = 0; ii < slReq.length; ii++) slReq[ii].scan(
          paths[i],
          cu,
          r[ii]
        );
      for (int ii = 0; ii < hlReq.length; ii++) hlReq[ii].scan(
          paths[i],
          cu,
          r[ii + slReq.length]
        );
    }

    return r;
  }

  // TODO: progress monitor
  // TODO: remove deleted files getMissing()
  public String[] getChangedFiles() throws IOException {
    if (this.proj.hasRepo()) {
      Repository repo = this.proj.getRepo();
      repo.scanForRepoChanges();
      IndexDiff diff = new IndexDiff(repo, "HEAD", new FileTreeIterator(repo));
      if (!diff.diff()) return new String[] {};
      Set<String> files = diff.getAdded();
      files.addAll(diff.getChanged());
      // TODO: add warning to commit files else wont be scanned
      // files.addAll(diff.getModified());
      return files.toArray(new String[files.size()]);
    } else {
      File[] files = new File(this.proj.getPath())
        .listFiles(f -> f.isFile() && f.getName().endsWith(".java"));
      String[] filePaths = new String[files.length];
      for (int i = 0; i < files.length; i++) filePaths[i] = files[i].getPath();
      return filePaths;
    }
  }
}
