package com.perseuspotter.ia;

import com.perseuspotter.ia.gui.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class JFXEntry extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage prStage) {
    prStage.setTitle("IATils");
    MainMenu menu = new MainMenu();
    prStage.setScene(menu.getScene());
    prStage.show();
  }
}