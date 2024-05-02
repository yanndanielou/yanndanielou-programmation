package tetris.rules;

/**
 * https://tetris.wiki/Drop#Gravity
 */
public class DropSpeed {
	/***
	 * 1G = 1 cell per frame, and 0.1G = 1 cell per 10 frames 
	 */
	public float gravity;
	
	private static final int NUMBER_OF_FRAMES_PER_SECOND = 50; 
	private static final int NUMBER_OF_MILLISECOND_PER_FRAME =  1000/50;

	public DropSpeed(float g) {

	}

	/**
	 * 1G = 1 cell per frame, and 0.1G = 1 cell per 10 frames
	 * @return
	 */
	public int getNumberOfMillisecondsBetweenEachStepDown() {
		float millisecondInFloat = NUMBER_OF_MILLISECOND_PER_FRAME * gravity;
		int millisecond = (int) millisecondInFloat;
		return millisecond;
				/*
	gravity = cell / 20ms
	15 = cell / 20ms
	cell = 20ms * 15
	*/
	
	

	}

}
