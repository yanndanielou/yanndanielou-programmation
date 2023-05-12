package game;

import game_board.Square;

public interface SquareListener {

	public void on_listen_to_square(Square square);

	public void on_square_status_changed(Square square);

}
