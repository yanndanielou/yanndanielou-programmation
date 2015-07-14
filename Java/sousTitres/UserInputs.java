import java.io.File;

public class UserInputs {

	private Action action;
	private File inputSubtitlesFile;
	int nombreMilisecondsModification;
	int minuteDebutModifications ;

	float balanceRatio;


	public void setInputSubtitlesFile(File inputSubtitlesFile) {
		this.inputSubtitlesFile = inputSubtitlesFile;
	}

	public File getInputSubtitlesFile() {
		return inputSubtitlesFile;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
	public int getMinuteDebutModifications() {
		return minuteDebutModifications;
	}
	public int getNombreMilisecondsModification() {
		return nombreMilisecondsModification;
	}
	public void setMinuteDebutModifications(int minuteDebutModifications) {
		this.minuteDebutModifications = minuteDebutModifications;
	}
	public void setNombreMilisecondsModification(int nombreMilisecondsModification) {
		this.nombreMilisecondsModification = nombreMilisecondsModification;
	}
	
	public void setBalanceRatio(float balanceRatio) {
		this.balanceRatio = balanceRatio;
	}
	public float getBalanceRatio() {
		return balanceRatio;
	}
}
