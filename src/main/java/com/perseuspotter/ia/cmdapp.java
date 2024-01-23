package com.perseuspotter.ia;

import com.perseuspotter.ia.scan.UMLBuilder;
import com.perseuspotter.ia.util.Project;
import java.io.IOException;

class cmdapp {

  public static void main(String args[]) throws IOException {
    Project proj = new Project(
      "C:\\Users\\ziyan\\OneDrive\\Documents\\Coding\\Java\\ia"
    );
    System.out.println(new UMLBuilder(proj).build());
  }
}
