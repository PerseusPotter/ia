package com.perseuspotter.ia.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.yaml.snakeyaml.Yaml;

public final class Settings {

  public static class _settings implements java.io.Serializable {

    public ArrayList<String> projects;
  }

  private static _settings INSTANCE = null;

  private static final String fileName = "./settings.txt";
  private static Logger logger = new Logger("Settings");

  public static _settings get() {
    return INSTANCE;
  }

  public static void save() throws IOException {
    new Yaml().dump(INSTANCE, new FileWriter(fileName));
  }

  public static void load() {
    try {
      INSTANCE =
        new Yaml().loadAs(new FileInputStream(fileName), _settings.class);
    } catch (FileNotFoundException e) {
      logger.log(
        "failed to load setttings, using default\n" + e.toString(),
        Logger.Level.ERROR
      );
      INSTANCE =
        new Yaml()
          .load(
            Settings.class.getClassLoader().getResourceAsStream("settings.yaml")
          );
    }
  }

  {
    Settings.load();
  }
}
