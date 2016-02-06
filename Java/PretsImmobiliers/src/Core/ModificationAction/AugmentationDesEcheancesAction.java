/*
 * Controlguide
 * Copyright (c) Siemens AG 2016, All Rights Reserved, Confidential
 */
package Core.ModificationAction;

public class AugmentationDesEcheancesAction extends ModificationEcheanceAction {
  private Integer augmentationPourcentage;

  public AugmentationDesEcheancesAction() {
  }

  public void setAugmentationPourcentage(Integer augmentationPourcentage) {
    this.augmentationPourcentage = augmentationPourcentage;
  }

  @Override
  public String toString() {
    if (augmentationPourcentage == null) {
      return "Augmentation";
    } else {
      return "Augmentation :" + augmentationPourcentage + "%";
    }
  }
}
