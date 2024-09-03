package secondregard.helpers;

import java.util.ArrayList;
import java.util.List;

public interface SecondRegardParams {

	// public static final List<String> ALLOWED_USER_PRENOMS =
	// CollectionUtils.asList("Nadia", "Lionel");
	public static final List<String> ALLOWED_USER_PRENOMS = new ArrayList<String>();
	// CollectionUtils.asList("Nadia");

	public static final List<String> ALLOWED_USER_ENTITES = new ArrayList<String>();; // =
																						// CollectionUtils.asList("SYSTEREL");

	public static final boolean GENERATE_ALSO_UNPROTECTED_PDF_FOR_NO_USER = false;

	public static final boolean GENERATE_ALSO_ZIP_FILES = true;
	
	public static final boolean PROCESS_PDFS = false;
	public static final boolean PROCESS_ZIPS = true;

}