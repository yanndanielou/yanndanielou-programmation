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
  private static final long serialVersionUID = -2416668889091277077L;

  public ItunesLibraryFileChooser() {
  }

  public File retrieveItunesLibraryFile() {
    JFileChooser inputFileChooser = new JFileChooser();
    //To be deleted
    inputFileChooser.setCurrentDirectory(new File("D:\\temp\\itunes_librarires"));
    FileFilter fileFilter = new FileNameExtensionFilter("Itunes libraries extensions", "txt", "xml");
    inputFileChooser.setFileFilter(fileFilter);
    inputFileChooser.setDialogTitle("Itunes library");
    inputFileChooser.showOpenDialog(this);
    return inputFileChooser.getSelectedFile();
  }
}
