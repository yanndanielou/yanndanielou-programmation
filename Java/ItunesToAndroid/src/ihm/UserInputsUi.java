package ihm;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import core.UserInputs;

public class UserInputsUi extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = -2416668889091277077L;

  public UserInputsUi() {
  }

  public UserInputs retrieveUserInputs() {
    UserInputs userInputs = new UserInputs();

    JFileChooser inputFileChooser = new JFileChooser();
    inputFileChooser.setDialogTitle("Itunes library");
    inputFileChooser.showOpenDialog(this);
    userInputs.setItunesLibraryFile(inputFileChooser.getSelectedFile());

    return userInputs;
  }
}
