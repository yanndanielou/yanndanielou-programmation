package tetris.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * https://tetris.wiki/Marathon
 */
public class MarathonMode extends GameMode {

	List<Double> speedInGByLevelNumber = DoubleStream.of(0.01667, 0.021017, 0.026977, 0.03525, 0.04693, 0.0636, 0.0879,
			0.1236, 0.1775, 0.259, 0.38, 0.59, 0.92, 1.46, 2.36, 3.91, 6.61, 11.43, 20)
            .boxed()
            .collect(Collectors.toCollection(ArrayList::new));
			
	public MarathonMode() {
	}
	
	@Override
	public DropSpeed getDropSpeedPerLevelNumber(int levelNumber) {
		double number = speedInGByLevelNumber.get(levelNumber);
		DropSpeed dropSpeed = new DropSpeed((float) number);
		return dropSpeed;
	}
}
