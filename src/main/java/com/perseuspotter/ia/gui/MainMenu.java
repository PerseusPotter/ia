package com.perseuspotter.ia.gui;

import com.perseuspotter.ia.JFXEntry;
import com.perseuspotter.ia.scan.Scanner;
import com.perseuspotter.ia.util.Project;
import com.perseuspotter.ia.util.Settings;
import java.io.IOException;
import java.util.regex.Pattern;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainMenu extends BasePanel {

  public MainMenu() {
    super("IATils");
    ScrollPane content = this.getContent();
    FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL);
    flowPane.setHgap(20);
    flowPane.setPadding(new Insets(50, 100, 50, 100));
    content.setContent(flowPane);

    Text t1 = new Text("New");
    Text t2 = new Text("+");
    t1.getStyleClass().add("title");
    t2.getStyleClass().add("title");
    addText(t1);
    addMuted(t2);
    StackPane addNewProj = new StackPane(new VBox(new StackPane(t1), new StackPane(t2)));
    addNewProj.setMinSize(100, 100);
    addButton(addNewProj);
    addNewProj.setOnMouseClicked(evn -> JFXEntry.changeScene(new NewProjectMenu().getScene()));
    flowPane.getChildren().add(addNewProj);

    Settings
      .get()
      .projects.forEach(p -> {
        try {
          Project proj = new Project(p);
          StackPane sp = this.createProjectBox(proj);
          // sp.setOnMouseClicked(evn -> {});
          flowPane.getChildren().add(sp);
        } catch (IOException e) {}
      });
  }

  private StackPane createProjectBox(Project proj) {
    String shortName = Pattern.compile("(?<=(?:\\|/))([^.\s\\/]+?)(?=\\$|/$|$)").matcher(proj.getPath()).group(0);
    int numReq = proj.getNumReqsCompleted();
    int totalReq = proj.isSl() ? Scanner.slReq.length : Scanner.hlReq.length;
    String numReqS = (numReq < 0 ? "?" : Integer.toString(numReq)) + " / " + totalReq;

    StackPane root = new StackPane();
    root.setMinSize(100, 100);
    addButton(root);
    VBox vb = new VBox();
    Text t1 = new Text(shortName);
    Text t2 = new Text(numReqS);
    addText(t1);
    addText(t2);
    HBox hb = new HBox();
    Button edit = new Button("Edit");
    Button del = new Button("Delete");
    addBackground(edit);
    addBackground(del);
    hb.getChildren().addAll(edit, del);
    vb.getChildren().addAll(new StackPane(t1), new StackPane(t2), new StackPane(hb));
    root.getChildren().add(vb);

    return root;
  }
}
