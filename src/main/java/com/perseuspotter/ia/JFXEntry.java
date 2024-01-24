package com.perseuspotter.ia;

import com.perseuspotter.ia.gui.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JFXEntry extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage prStage) {
    s = prStage;
    prStage.setTitle("IATils");
    prStage.setWidth(800);
    prStage.setHeight(500);

    MainMenu menu = new MainMenu();
    prStage.setScene(menu.getScene());
    prStage.show();
  }

  // stfu ok no multi window please
  private static Stage s;

  public static void changeScene(Scene sc) {
    s.setScene(sc);
  }
}
