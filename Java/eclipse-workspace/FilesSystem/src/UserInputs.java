import java.io.File;

public class UserInputs {

	private File filesDirectory;
	int nombreMilisecondsModification;
	int minuteDebutModifications;

	float balanceRatio;

	public void setFilesDirectory(File filesDirectory) {
		this.filesDirectory = filesDirectory;
	}

	public File getFilesDirectory() {
		return filesDirectory;
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
