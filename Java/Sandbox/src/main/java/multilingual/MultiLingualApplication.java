package multilingual;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * https://simplelocalize.io/blog/posts/java-internationalization/
 */
public class MultiLingualApplication {

	public static void main(String[] args) {
		Locale locale = Locale.of("pl", "PL");

		ResourceBundle resourceBundle = ResourceBundle.getBundle("package.ExampleResource", locale);

		String pattern = resourceBundle.getString("example.dynamicresource");
		String dateAsString = "today";
		String message = MessageFormat.format(pattern, dateAsString);
	}

}
