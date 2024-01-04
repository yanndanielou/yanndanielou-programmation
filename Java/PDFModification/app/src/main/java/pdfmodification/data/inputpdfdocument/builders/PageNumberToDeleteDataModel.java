package pdfmodification.data.inputpdfdocument.builders;

public class PageNumberToDeleteDataModel {

	private Integer pageNumber;
	private Integer offset;

	public int getPageNumber() {
		return pageNumber - offset;
	}

}
