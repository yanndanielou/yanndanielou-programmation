package common.builders;

import java.util.ArrayList;
import java.util.List;

public class IntegersDataModel extends NamedDataModel {

	private Integer lowerInteger;
	private Integer upperInteger;

	public List<Integer> computeAllIntegers() {
		List<Integer> allIntegers = new ArrayList<Integer>();
		for (int i = lowerInteger; i < upperInteger; i++) {
			allIntegers.add(i);
		}
		return allIntegers;
	}

}
