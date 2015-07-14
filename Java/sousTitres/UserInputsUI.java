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
		inputFileChooser.setDialogTitle("Fichier de sous titres");
		inputFileChooser.showOpenDialog(this);
		userInputs.setInputSubtitlesFile(inputFileChooser.getSelectedFile());

		char CHOIX_AJOUTER_TEMPS = 'a';
		char CHOIX_RETIRER_TEMPS = 'r';
		char CHOIX_EQUILIBRER_TEMPS = 'e';

		Scanner sc = new Scanner(System.in);
		System.out.println(
				"Souhaitez vous ajouter (tapez " + CHOIX_AJOUTER_TEMPS + ") ou retirer (tapez " + CHOIX_RETIRER_TEMPS
						+ ") du temps, ou bien disperser le temps (tapez " + CHOIX_EQUILIBRER_TEMPS + ")??\n");
		char choix = sc.nextLine().charAt(0);

		Action action = null;
		if (choix == CHOIX_AJOUTER_TEMPS) {
			action = Action.ADD_TIME;
		} else if (choix == CHOIX_RETIRER_TEMPS) {
			action = Action.REMOVE_TIME;
		} else if (choix == CHOIX_EQUILIBRER_TEMPS) {
			action = Action.BALANCE_TIME;
		}
		userInputs.setAction(action);

		if (action == Action.ADD_TIME || action == Action.REMOVE_TIME) {
			System.out.println(
					"Combien de temps voulez vous ajouter ou retirer? Tapez en chiffres le temps en miliseconds)\n");
			userInputs.setNombreMilisecondsModification(sc.nextInt());

			System.out.println(
					"A partir de quand souhaitez vous modifier le temps des sous titres? Tapez en chiffre la minute de debut des modifications (0 pour des le debut)\n");
			userInputs.setMinuteDebutModifications(sc.nextInt());
		}else if(action == Action.BALANCE_TIME){
			System.out.println(
					"Quelle est le timestamp en secondes du dernier dialogue dans le fichier de sous titres\n");
			int lastDialogTimestampInSubtitleFileInSeconds = sc.nextInt();
			
			System.out.println(
					"Quelle est le timestamp en secondes de ce meme dialogue dans le film (fichier video)\n");
			int lastDialogTimestampInMovieInSeconds = sc.nextInt();
			
			float balanceRatio = (float)lastDialogTimestampInMovieInSeconds / lastDialogTimestampInSubtitleFileInSeconds;
			userInputs.setBalanceRatio(balanceRatio);
		}

		sc.close();
	}

}
