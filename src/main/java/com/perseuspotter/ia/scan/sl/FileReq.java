package com.perseuspotter.ia.scan.sl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.perseuspotter.ia.scan.Requirement;
import com.perseuspotter.ia.scan.Result;
import java.util.ArrayList;
import java.util.List;

public class FileReq extends Requirement {

  public Result init() {
    return new Result("File I/O", "use some sort of fs", true);
  }

  private static List<String> io = new ArrayList<String>();

  static {
    io.add("File");
    io.add("FileInputStream");
    io.add("FileOutputStream");
    io.add("FileReader");
    io.add("FileWriter");
    io.add("PrintWriter");
    io.add("PrintStream");
    io.add("RandomAccessFile");
  }

  public void scan(String fp, CompilationUnit cu, Result res) {
    cu
      .findAll(ObjectCreationExpr.class, m -> io.contains(m.getTypeAsString()))
      .forEach(m -> this.addRes(fp, res, m));
  }
}
