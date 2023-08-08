package geometry;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegerRectangle {
	private static final Logger LOGGER = LogManager.getLogger(IntegerRectangle.class);

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

	public IntegerRectangle(IntegerRectangle r) {
		awtRectangle = new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight());
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

	/***
	 * Create IntegerRectangle from a list of points
	 * 
	 * @param rectangleAsListOfPoints
	 */
	public IntegerRectangle(List<? extends Point> rectangleAsListOfPoints) {

		Point topLeftPoint = getTopLeftPoint(rectangleAsListOfPoints);
		Point topRightPoint = getTopRightPoint(rectangleAsListOfPoints);
		Point bottomLeftPoint = getBottomLeftPoint(rectangleAsListOfPoints);
		Point bottomRightPoint = getBottomRightPoint(rectangleAsListOfPoints);

		double upSideLength = topLeftPoint.distance(topRightPoint);
		double leftSideLength = topLeftPoint.distance(bottomLeftPoint);
		double bottomSideLength = bottomLeftPoint.distance(bottomRightPoint);
		double rightSideSize = topRightPoint.distance(bottomRightPoint);

		double topLeftToBottomRightDiagonalLength = topLeftPoint.distance(bottomRightPoint);
		double topRightToBottomLeftDiagonalLength = topRightPoint.distance(bottomLeftPoint);

		if (upSideLength != bottomSideLength) {
			throw new BadGeometryException("Quadrilateral is not a parrallelogram");
		}
		if (leftSideLength != rightSideSize) {
			throw new BadGeometryException("Quadrilateral is not a parrallelogram");
		}
		if (topLeftToBottomRightDiagonalLength != topRightToBottomLeftDiagonalLength) {
			throw new BadGeometryException("Quadrilateral is not a rectangle");
		}

		double rectangleTheoricalArea = upSideLength * leftSideLength;
		if (rectangleTheoricalArea != rectangleAsListOfPoints.size()) {
			LOGGER.fatal("Actual size: " + rectangleAsListOfPoints.size() + " does not match theorical area:"
					+ rectangleTheoricalArea);
		}

		awtRectangle = new Rectangle(topLeftPoint, new Dimension((int) upSideLength, (int) leftSideLength));

	}

	public List<IntegerRectangle> getInnerSubRectangles(Dimension subRectanglesDimension) {
		List<IntegerRectangle> innerSubRectangles = new ArrayList<>();

		for (int yIter = getY(); yIter < getMaxY(); yIter += subRectanglesDimension.getHeight()) {
			for (int xIter = getX(); xIter < getMaxX(); xIter += subRectanglesDimension.getWidth()) {
				Point innerSubRectangleTopLeft = new Point(xIter, yIter);
				IntegerRectangle innerSubRectangle = new IntegerRectangle(innerSubRectangleTopLeft,
						subRectanglesDimension);
				innerSubRectangles.add(innerSubRectangle);
			}
		}

		return innerSubRectangles;
	}

	private Point getTopLeftPoint(List<? extends Point> points) {
		double minXFound = points.get(0).getX();
		double minYFound = points.get(0).getY();
		Point topLeftPoint = points.get(0);
		for (Point point : points) {
			if (point.getX() < minXFound) {
				minXFound = point.getX();
			}
			if (point.getY() < minYFound) {
				minYFound = point.getY();
			}
			if (point.getX() <= minXFound && point.getY() <= minYFound) {
				topLeftPoint = point;
			}
		}
		return topLeftPoint;
	}

	private Point getTopRightPoint(List<? extends Point> points) {
		double maxXFound = points.get(0).getX();
		double minYFound = points.get(0).getY();
		Point topRightPoint = points.get(0);
		for (Point point : points) {
			if (point.getX() > maxXFound) {
				maxXFound = point.getX();
			}
			if (point.getY() < minYFound) {
				minYFound = point.getY();
			}
			if (point.getX() >= maxXFound && point.getY() <= minYFound) {
				topRightPoint = point;
			}
		}
		return topRightPoint;
	}

	private Point getBottomLeftPoint(List<? extends Point> points) {
		double minXFound = points.get(0).getX();
		double maxYFound = points.get(0).getY();
		Point bottomLeftPoint = points.get(0);
		for (Point point : points) {
			if (point.getX() < minXFound) {
				minXFound = point.getX();
			}
			if (point.getY() > maxYFound) {
				maxYFound = point.getY();
			}
			if (point.getX() <= minXFound && point.getY() >= maxYFound) {
				bottomLeftPoint = point;
			}
		}
		return bottomLeftPoint;
	}

	private Point getBottomRightPoint(List<? extends Point> points) {
		double maxXFound = points.get(0).getX();
		double maxYFound = points.get(0).getY();
		Point bottomRightPoint = points.get(0);
		for (Point point : points) {
			if (point.getX() > maxXFound) {
				maxXFound = point.getX();
			}
			if (point.getY() > maxYFound) {
				maxYFound = point.getY();
			}
			if (point.getX() >= maxXFound && point.getY() >= maxYFound) {
				bottomRightPoint = point;
			}
		}
		return bottomRightPoint;
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

	public List<IntegerPoint> getAllPoints() {
		ArrayList<IntegerPoint> allPoints = new ArrayList<>();
		for (int x_point_it = getX(); x_point_it < getMaxX(); x_point_it++) {
			for (int y_point_it = getY(); y_point_it < getMaxY(); y_point_it++) {
				allPoints.add(new IntegerPoint(x_point_it, y_point_it));
			}
		}

		return allPoints;
	}

}
