package ihm;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.UserInputs;

public class ItunesLibraryFileChooser extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1067643505599785851L;

  public ItunesLibraryFileChooser() {
  }

  public File retrieveItunesLibraryFile() {
    JFileChooser inputFileChooser = new JFileChooser();
    //To be deleted
    inputFileChooser.setCurrentDirectory(new File("D:\\temp\\itunes2Android__tests"));
    FileFilter fileFilter = new FileNameExtensionFilter("Itunes libraries extensions", "xml");
    inputFileChooser.setFileFilter(fileFilter);
    inputFileChooser.setDialogTitle("Itunes library");
    inputFileChooser.showOpenDialog(this);
    return inputFileChooser.getSelectedFile();
  }
}
