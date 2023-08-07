package geometry;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class IntegerRectangle {

	private Rectangle awtRectangle;

	public IntegerRectangle() {
		awtRectangle = new Rectangle(0, 0, 0, 0);
	}

	/**
	 * Constructs a new {@code Rectangle}, initialized to match the values of the
	 * specified {@code Rectangle}.
	 * 
	 * @param r the {@code Rectangle} from which to copy initial values to a newly
	 *          constructed {@code Rectangle}
	 * @since 1.1
	 */
	public IntegerRectangle(Rectangle r) {
		awtRectangle = new Rectangle(r.x, r.y, r.width, r.height);
	}

	public IntegerRectangle(int x, int y, int width, int height) {
		awtRectangle = new Rectangle(x, y, width, height);
	}

	public IntegerRectangle(int width, int height) {
		awtRectangle = new Rectangle(0, 0, width, height);
	}

	public IntegerRectangle(Point p, Dimension d) {
		awtRectangle = new Rectangle(p.x, p.y, d.width, d.height);
	}

	public IntegerRectangle(Point p) {
		awtRectangle = new Rectangle(p.x, p.y, 0, 0);
	}

	public IntegerRectangle(Dimension d) {
		awtRectangle = new Rectangle(0, 0, d.width, d.height);
	}

	public int getX() {
		return (int) awtRectangle.getX();
	}

	public int getY() {
		return (int) awtRectangle.getY();
	}

	public int getMaxX() {
		return (int) awtRectangle.getX();
	}

	public int getMaxY() {
		return (int) awtRectangle.getY();
	}

	public int getWidth() {
		return (int) awtRectangle.getWidth();
	}

	public int getHeight() {
		return (int) awtRectangle.getHeight();
	}

	public void translate(int dx, int dy) {
		awtRectangle.translate(dx, dy);
	}

	public List<Point> getAllPoints() {
		ArrayList<Point> allPoints = new ArrayList<>();
		for (int x_point_it = getX(); x_point_it < getMaxX(); x_point_it++) {
			for (int y_point_it = getY(); y_point_it < getMaxY(); y_point_it++) {
				allPoints.add(new Point(x_point_it, y_point_it));
			}
		}

		return allPoints;
	}

}
