package cheat_codes;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheatCodeManager {

	private static CheatCodeManager instance;
	private static final Logger LOGGER = LogManager.getLogger(CheatCodeManager.class);

	private CheatCodeManager() {
	}

	public static CheatCodeManager getInstance() {
		if (instance == null) {
			instance = new CheatCodeManager();
		}
		return instance;
	}

	public boolean try_and_apply_text_cheat_code(String text_cheat_code) {
		LOGGER.info("text_cheat_code_entered:" + text_cheat_code);
		return false;
	}

}
