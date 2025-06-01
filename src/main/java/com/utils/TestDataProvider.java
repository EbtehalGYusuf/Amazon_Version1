package com.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.DataProvider;

public class TestDataProvider {

  private static final String CSV_PATH = "src/test/resources/testdata/searchData.csv";

  @DataProvider(name = "searchData", parallel = true)
  public static Object[][] searchData(Method m) {
    List<String> lines = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
      br.readLine(); // skip header
      String l;
      while ((l = br.readLine()) != null) {
        lines.add(l.trim());
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed reading CSV", e);
    }

    String text;
    switch (m.getName()) {
      case "case01_SearchFilterSelectSpecificVideo":
        text = lines.get(0);
        break;
      case "case02_SearchFilterSelectSpecificVideo":
        text = lines.get(1);
        break;
      case "case03_SearchFilterSelectSpecificVideo":
        text = lines.get(0);
        break;
      default:
        throw new IllegalArgumentException("No CSV mapping for " + m.getName());
    }

    return new Object[][] {{text}};
  }
}
