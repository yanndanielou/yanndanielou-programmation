package log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4JConfig {

	// Récupération de notre logger.
	private static final Logger LOGGER = LogManager.getLogger(Log4JConfig.class);

	public Log4JConfig() {

		// On produit un log de niveau informatif.
		LOGGER.info("Hello World with Log4J 2");

		// On produit un log de niveau erreur.
		LOGGER.error("Houston, we have a problem");
	}
}
