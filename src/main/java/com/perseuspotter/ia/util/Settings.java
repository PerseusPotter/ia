package com.perseuspotter.ia.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.yaml.snakeyaml.Yaml;

public final class Settings {

  public record InternalProject(String path, boolean isSl) {}

  public static class InternalSettings implements java.io.Serializable {

    public InternalSettings() {}

    public InternalSettings(
      ArrayList<InternalProject> projects,
      boolean isDarkMode,
      String preferredIDE
    ) {
      this.projects = projects;
      this.isDarkMode = isDarkMode;
      this.preferredIDE = preferredIDE;
    }

    public ArrayList<InternalProject> projects;
    public boolean isDarkMode;
    public String preferredIDE;
  }

  private static InternalSettings INSTANCE = null;

  private static final String fileName = "./settings.txt";
  private static Logger logger = new Logger("Settings");

  static {
    Settings.load();
  }

  public static InternalSettings get() {
    return INSTANCE;
  }

  public static void save() throws IOException {
    new Yaml().dump(INSTANCE, new FileWriter(fileName));
  }

  {
    Runtime
      .getRuntime()
      .addShutdownHook(
        new Thread() {
          public void run() {
            try {
              save();
            } catch (IOException e) {}
          }
        }
      );
  }

  public static void load() {
    try {
      INSTANCE =
        new Yaml()
          .loadAs(new FileInputStream(fileName), InternalSettings.class);
    } catch (FileNotFoundException e) {
      logger.log(
        "failed to load setttings, using default\n" + e.toString(),
        Logger.Level.ERROR
      );
      INSTANCE =
        new Yaml()
          .loadAs(
            Settings.class.getResourceAsStream("/settings.yaml"),
            InternalSettings.class
          );
    }
  }
}
