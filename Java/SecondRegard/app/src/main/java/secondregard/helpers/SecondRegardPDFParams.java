package secondregard.helpers;

import java.util.ArrayList;
import java.util.List;

import common.collection.CollectionUtils;

public interface SecondRegardPDFParams {

	// public static final List<String> ALLOWED_USER_PRENOMS =
	// CollectionUtils.asList("Nadia", "Lionel");
	public static final List<String> ALLOWED_USER_PRENOMS = new ArrayList<String>();
	// CollectionUtils.asList("Nadia");

	public static final List<String> ALLOWED_USER_ENTITES = new ArrayList<String>();; // =
																						// CollectionUtils.asList("SYSTEREL");

	public static final boolean GENERATE_ALSO_UNPROTECTED_PDF_FOR_NO_USER = false;

	public static final boolean GENERATE_ALSO_ZIP_FILES = true;

}