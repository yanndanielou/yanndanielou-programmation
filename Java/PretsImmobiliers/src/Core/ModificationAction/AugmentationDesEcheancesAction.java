/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package Core.ModificationAction;

public class AugmentationDesEcheancesAction extends ModificationEcheanceAction {
  private int augmentationPourcentage;

  public AugmentationDesEcheancesAction(int augmentationPourcentage) {
    this.augmentationPourcentage = augmentationPourcentage;
  }

  @Override
  public String toString() {
    return "Augmentation :" + augmentationPourcentage + "%";
  }
}
