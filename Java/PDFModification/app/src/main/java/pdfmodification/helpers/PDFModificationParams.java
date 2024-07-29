package pdfmodification.helpers;

import java.util.ArrayList;
import java.util.List;

import common.collection.CollectionUtils;

public interface PDFModificationParams {

	// public static final List<String> ALLOWED_USER_PRENOMS =
	//CollectionUtils.asList("Nadia", "Lionel");
	public static final List<String> ALLOWED_USER_PRENOMS = CollectionUtils.asList("Nadia");
	//new ArrayList<String>();

	public static final List<String> ALLOWED_USER_ENTITES = new ArrayList<String>();; //= CollectionUtils.asList("SYSTEREL");

	public static final boolean GENERATE_ALSO_UNPROTECTED_PDF_FOR_NO_USER = false;

	public static final boolean GENERATE_ALSO_ZIP_FILES = false;

}