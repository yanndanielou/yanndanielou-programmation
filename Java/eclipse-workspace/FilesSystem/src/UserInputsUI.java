import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class UserInputsUI extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2020349223219828393L;

	public UserInputsUI() {

	}

	public void fillUserInputs(UserInputs userInputs) {

		JFileChooser inputFileChooser = new JFileChooser();
		inputFileChooser.setDialogTitle("Répertoire des fichiers");
		inputFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		inputFileChooser.showOpenDialog(this);
		userInputs.setFilesDirectory(inputFileChooser.getSelectedFile());
	}

}
