package tictactoeblueskin.application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class SquareSkin extends StackPane {
	static final Image noughtImage = new Image(
			"http://icons.iconarchive.com/icons/double-j-design/origami-colored-pencil/128/green-cd-icon.png");
	public static final Image crossImage = new Image(
			"http://icons.iconarchive.com/icons/double-j-design/origami-colored-pencil/128/blue-cross-icon.png");

	private final ImageView imageView = new ImageView();

	SquareSkin(final Square square) {
		getStyleClass().add("square");

		imageView.setMouseTransparent(true);

		getChildren().setAll(imageView);
		setPrefSize(crossImage.getHeight() + 20, crossImage.getHeight() + 20);

		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				square.pressed();
			}
		});

		square.stateProperty().addListener(new ChangeListener<Square.State>() {
			@Override
			public void changed(ObservableValue<? extends Square.State> observableValue, Square.State oldState,
					Square.State state) {
				switch (state) {
				case EMPTY:
					imageView.setImage(null);
					break;
				case NOUGHT:
					imageView.setImage(noughtImage);
					break;
				case CROSS:
					imageView.setImage(crossImage);
					break;
				}
			}
		});
	}
}