package main.builders.belligerents;

public abstract class GameObjectDataModel {

	protected String description;
	protected String name;

	protected Integer height;
	protected Integer width;

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getWidth() {
		return width;
	}

}
