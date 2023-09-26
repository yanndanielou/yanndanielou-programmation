
package towerdefense.hmi;

import java.awt.Frame;

import game.hmi.GenericCheatCodeDialog;
import towerdefense.cheatcodes.CheatCodeManager;

public class CheatCodeDialog extends GenericCheatCodeDialog {
	
	private static final long serialVersionUID = -8774098069242051538L;

	public CheatCodeDialog(Frame aFrame) {
		super(aFrame);
	}

	protected boolean tryAndApplyTextCheatCode(String typedText) {
		return CheatCodeManager.getInstance().tryAndApplyTextCheatCode(typedText);
	}
}
