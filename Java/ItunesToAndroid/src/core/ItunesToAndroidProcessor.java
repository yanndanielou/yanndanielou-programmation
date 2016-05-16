package core;

import ihm.UserInputsUi;

public class ItunesToAndroidProcessor {

  private UserInputs userInputs;
  private UserInputsUi userInputsUi;

  public void run() {

    userInputsUi = new UserInputsUi();
    userInputs = userInputsUi.retrieveUserInputs();

  }
}
