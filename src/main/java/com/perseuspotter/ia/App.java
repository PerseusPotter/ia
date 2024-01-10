package com.perseuspotter.ia;

import com.perseuspotter.ia.scan.UMLBuilder;
import com.perseuspotter.ia.util.Project;
import java.io.IOException;
import javax.swing.*;

class App {

  public static void main(String args[]) throws IOException {
    Project proj = new Project(
      "C:\\Users\\ziyan\\OneDrive\\Documents\\Coding\\Java\\ia"
    );
    System.out.println(new UMLBuilder(proj).build());

    JFrame frame = new JFrame("My First GUI");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 300);
    JButton button = new JButton("Press");
    frame.getContentPane().add(button); // Adds Button to content pane of frame
    frame.setVisible(true);
  }
}
