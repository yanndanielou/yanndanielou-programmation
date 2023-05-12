package game_board;

public class Square {
	
	private boolean contains_mine;
	private boolean is_discovered = false;;
	private boolean is_flagged = false;;
	private boolean has_explosed = false;
	private int number_of_neighbor_mines;
	

	public Square() {
		// TODO Auto-generated constructor stub
	}


	public boolean isContains_mine() {
		return contains_mine;
	}


	public boolean isIs_discovered() {
		return is_discovered;
	}


	public boolean isIs_flagged() {
		return is_flagged;
	}


	public boolean isHas_explosed() {
		return has_explosed;
	}


	public int getNumber_of_neighbor_mines() {
		return number_of_neighbor_mines;
	}


	public void setContains_mine(boolean contains_mine) {
		this.contains_mine = contains_mine;
	}

}
