package com.perseuspotter.ia.gui;

import com.perseuspotter.ia.util.Settings;
import com.perseuspotter.ia.util.Settings.InternalSettings;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class BasePanel {

  protected Scene scene;
  protected ScrollPane juice;

  public BasePanel(String name) {
    Text title = new Text(name);
    title.getStyleClass().add("title");
    addText(title);

    juice = new ScrollPane();
    juice.pannableProperty().set(true);
    juice.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
    juice.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    addScrollPane(juice);

    Button settings = new Button("SETTINGS");
    settings.setPadding(new Insets(10));
    settings.setOnAction(evn -> {
      Dialog<InternalSettings> d = SettingsDialogueFactory.createDialog();
      d.showAndWait();
      InternalSettings r = d.getResult();
      if (r != null && !r.preferredIDE.equals("not scuffed")) {
        Settings.get().isDarkMode = r.isDarkMode;
        Settings.get().preferredIDE = r.preferredIDE;
        updateColors();
      }
    });
    addButton(settings);

    Text credits = new Text("v0.0.1\n© 2023-2024 Ziyang Qiu");
    addMuted(credits);

    BorderPane bottom = new BorderPane();
    bottom.setLeft(settings);
    bottom.setRight(credits);
    bottom.setPadding(new Insets(0, 20, 10, 20));

    VBox vbox = new VBox(new StackPane(title), juice, bottom);
    VBox.setVgrow(juice, Priority.ALWAYS);
    addBackground(vbox);

    this.scene = new Scene(vbox);
    this.scene.getStylesheets()
      .add(BasePanel.class.getResource("/stylesheet.css").toExternalForm());
  }

  public Scene getScene() {
    return this.scene;
  }

  public ScrollPane getContent() {
    return this.juice;
  }

  protected static List<ScrollPane> scrollPanes = new ArrayList<ScrollPane>();
  protected static List<Region> backgrounds = new ArrayList<Region>();
  protected static List<Text> textNodes = new ArrayList<Text>();
  protected static List<Text> mutedNodes = new ArrayList<Text>();
  protected static List<Button> buttons = new ArrayList<Button>();

  public static void updateColors() {
    scrollPanes.forEach(n -> {
      n
        .getStyleClass()
        .removeAll("bg-" + getAltVisMode(), "content-panel-" + getAltVisMode());
      n
        .getStyleClass()
        .addAll("bg-" + getVisMode(), "content-panel-" + getVisMode());
    });
    backgrounds.forEach(n -> {
      n.getStyleClass().removeAll("bg-" + getAltVisMode());
      n.getStyleClass().addAll("bg-" + getVisMode());
    });
    textNodes.forEach(n -> {
      n.getStyleClass().removeAll("txt-" + getAltVisMode());
      n.getStyleClass().addAll("txt-" + getVisMode());
    });
    mutedNodes.forEach(n -> {
      n.getStyleClass().removeAll("txt-mute-" + getAltVisMode());
      n.getStyleClass().addAll("txt-mute-" + getVisMode());
    });
    buttons.forEach(n -> {
      n.getStyleClass().removeAll("button-" + getAltVisMode());
      n.getStyleClass().addAll("button-" + getVisMode());
    });
  }

  public void _addScrollPane(ScrollPane v) {
    addScrollPane(v);
  }

  public void _addBackground(Region v) {
    addBackground(v);
  }

  public void _addText(Text v) {
    addText(v);
  }

  public void _addMuted(Text v) {
    addMuted(v);
  }

  public void _addButton(Button v) {
    addButton(v);
  }

  public static void addScrollPane(ScrollPane v) {
    scrollPanes.add(v);
    v
      .getStyleClass()
      .addAll("bg-" + getVisMode(), "content-panel-" + getVisMode());
  }

  public static void addBackground(Region v) {
    backgrounds.add(v);
    v.getStyleClass().add("bg-" + getVisMode());
  }

  public static void addText(Text t) {
    textNodes.add(t);
    t.getStyleClass().add("txt-" + getVisMode());
  }

  public static void addMuted(Text t) {
    mutedNodes.add(t);
    t.getStyleClass().add("txt-mute-" + getVisMode());
  }

  public static void addButton(Button b) {
    buttons.add(b);
    b.getStyleClass().add("button-" + getVisMode());
  }

  public static boolean isDarkMode() {
    return Settings.get().isDarkMode;
  }

  public static String getVisMode() {
    return isDarkMode() ? "dark" : "light";
  }

  public static String getAltVisMode() {
    return isDarkMode() ? "light" : "dark";
  }
}
