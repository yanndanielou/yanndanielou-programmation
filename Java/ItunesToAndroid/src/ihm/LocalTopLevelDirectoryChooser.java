package ihm;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.UserInputs;

public class LocalTopLevelDirectoryChooser extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 6656384427202070953L;

  public LocalTopLevelDirectoryChooser() {
  }

  public File retrieveLocalTopLevelFolder() {
    JFileChooser inputFileChooser = new JFileChooser();

    inputFileChooser.setCurrentDirectory(new File("D:\\temp"));
    inputFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    inputFileChooser.setDialogTitle("Local music directory");
    inputFileChooser.showOpenDialog(this);
    return inputFileChooser.getSelectedFile();
  }
}
