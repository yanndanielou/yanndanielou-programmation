package game.cheatcode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenericCheatCodeManager {

	protected static final Logger LOGGER = LogManager.getLogger(GenericCheatCodeManager.class);

	protected GenericCheatCodeManager() {
	}

	public boolean tryAndApplyTextCheatCode(String textCheatCode) {
		LOGGER.info("textCheatCodeEntered:" + textCheatCode);

		try {

			Method method = this.getClass().getDeclaredMethod(textCheatCode);
			method.invoke(this);
			return true;
		} catch (SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException
				| InvocationTargetException e) {

		}
		return false;
	}

}
