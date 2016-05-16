package ihm;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.UserInputs;

public class TargetTopLevelFolderChooser extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = -2416668889091277077L;

  public TargetTopLevelFolderChooser() {
  }

  public File retrieveTargetTopLevelFolder() {
    JFileChooser inputFileChooser = new JFileChooser();

    inputFileChooser.setCurrentDirectory(new File("D:\\temp"));
    inputFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    inputFileChooser.setDialogTitle("Target music directory");
    inputFileChooser.showOpenDialog(this);
    return inputFileChooser.getSelectedFile();
  }
}
