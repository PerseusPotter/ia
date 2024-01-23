package com.perseuspotter.ia.gui;

import com.perseuspotter.ia.util.Settings;
import com.perseuspotter.ia.util.Settings.InternalSettings;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public final class SettingsDialogueFactory {

  public static Dialog<InternalSettings> createDialog() {
    Dialog<InternalSettings> dialog = new Dialog<InternalSettings>();
    dialog.setTitle("Settings");
    dialog.setResizable(false);

    ToggleButton visToggle = new ToggleButton();
    if (Settings.get().isDarkMode) {
      visToggle.setSelected(true);
      visToggle.setText("Dark");
    } else {
      visToggle.setSelected(false);
      visToggle.setText("Light");
    }
    visToggle.setOnAction(evn -> {
      visToggle.setText(visToggle.isSelected() ? "Dark" : "Light");
      dialog.getDialogPane().getScene().getWindow().sizeToScene();
    });

    ChoiceBox<String> ide = new ChoiceBox<String>();
    ide
      .getItems()
      .addAll(
        "Default",
        "Notepad++",
        "VS Code",
        "Eclipse",
        "IntelliJ",
        "NetBeans"
      );
    ide
      .getSelectionModel()
      .select(
        Settings.get().preferredIDE.equals("")
          ? "Default"
          : Settings.get().preferredIDE
      );

    Button confirm = new Button("Confirm");
    confirm.setOnAction(evn -> {
      dialog.setResult(
        new InternalSettings(
          null,
          visToggle.isSelected(),
          ide.getSelectionModel().getSelectedItem()
        )
      );
      dialog.close();
    });

    Button cancel = new Button("Cancel");
    cancel.setOnAction(evn -> {
      dialog.close();
    });

    dialog
      .getDialogPane()
      .setContent(
        new VBox(new HBox(visToggle, ide), new HBox(confirm, cancel))
      );
    dialog.setResult(new InternalSettings(null, false, "not scuffed"));
    return dialog;
  }
}
