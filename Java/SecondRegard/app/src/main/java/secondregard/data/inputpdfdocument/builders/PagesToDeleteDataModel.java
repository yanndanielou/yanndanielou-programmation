package secondregard.data.inputpdfdocument.builders;

import java.util.List;

import common.collection.CollectionUtils;

public class PagesToDeleteDataModel {

	private Integer pageNumber;
	private List<Integer> pageNumbers;

	public List<Integer> getPageNumbers() {
		return pageNumber == null ? pageNumbers : CollectionUtils.asList(pageNumber);
	}

}
