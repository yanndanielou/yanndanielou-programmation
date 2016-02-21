package main;

import hmi.views.MainView;

import java.util.Locale;

public class PretsImmobiliersApplication {

  public static void main(String[] args) {
    Locale.setDefault(Locale.US);
    MainView.getInstance();
  }
}
