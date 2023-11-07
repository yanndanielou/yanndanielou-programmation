package geometry2d.integergeometry;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.random.RandomIntegerGenerator;
import geometry2d.exceptions.BadGeometryException;

public class IntegerPrecisionRectangle {
	private static final Logger LOGGER = LogManager.getLogger(IntegerPrecisionRectangle.class);

	private Rectangle awtRectangle;

	public IntegerPrecisionRectangle() {
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
	public IntegerPrecisionRectangle(Rectangle r) {
		awtRectangle = new Rectangle(r.x, r.y, r.width, r.height);
	}

	public IntegerPrecisionRectangle(IntegerPrecisionRectangle r) {
		awtRectangle = new Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}

	public IntegerPrecisionRectangle(int x, int y, int width, int height) {
		awtRectangle = new Rectangle(x, y, width, height);
	}

	public IntegerPrecisionRectangle(int width, int height) {
		awtRectangle = new Rectangle(width, height);
	}

	public IntegerPrecisionRectangle(Point p, Dimension d) {
		awtRectangle = new Rectangle(p.x, p.y, d.width, d.height);
	}

	public IntegerPrecisionRectangle(Point topLeft, Point bottomRight) {
		this(topLeft, new Dimension((int) (bottomRight.getX() - topLeft.getX()),
				(int) (bottomRight.getY() - topLeft.getY())));
	}

	public IntegerPrecisionRectangle(Point p) {
		awtRectangle = new Rectangle(p.x, p.y, 0, 0);
	}

	public IntegerPrecisionRectangle(Dimension d) {
		awtRectangle = new Rectangle(0, 0, d.width, d.height);
	}

	public static IntegerPrecisionRectangle getRectangleBoundingBoxOfPoints(
			List<? extends Point> rectangleAsListOfPoints) {

		double minX = getMinX(rectangleAsListOfPoints);
		double maxX = getMaxX(rectangleAsListOfPoints);
		double minY = getMinY(rectangleAsListOfPoints);
		double maxY = getMaxY(rectangleAsListOfPoints);

		return new IntegerPrecisionRectangle(new IntegerPrecisionPoint(minX, minY),
				new IntegerPrecisionPoint(maxX, maxY));
	}

	/***
	 * Create IntegerRectangle from a list of points
	 * 
	 * @param rectangleAsListOfPoints
	 */
	public IntegerPrecisionRectangle(List<? extends Point> rectangleAsListOfPoints) {

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

	public List<IntegerPrecisionRectangle> getInnerSubRectangles(Dimension subRectanglesDimension) {
		List<IntegerPrecisionRectangle> innerSubRectangles = new ArrayList<>();

		if (subRectanglesDimension.getWidth() <= 0) {
			throw new IllegalArgumentException("Width must be positive in " + subRectanglesDimension);
		}
		if (subRectanglesDimension.getHeight() <= 0) {
			throw new IllegalArgumentException("Height must be positive in " + subRectanglesDimension);
		}

		double subRectangleHeight = subRectanglesDimension.getHeight();
		for (int yIter = getY(); yIter + subRectangleHeight < getMaxY(); yIter += subRectangleHeight) {
			double subRectangleWidth = subRectanglesDimension.getWidth();
			for (int xIter = getX(); xIter + subRectangleWidth < getMaxX(); xIter += subRectangleWidth) {
				Point innerSubRectangleTopLeft = new Point(xIter, yIter);
				IntegerPrecisionRectangle innerSubRectangle = new IntegerPrecisionRectangle(innerSubRectangleTopLeft,
						subRectanglesDimension);
				innerSubRectangles.add(innerSubRectangle);
			}
		}

		return innerSubRectangles;
	}

	public Point getTopLeftPoint() {
		return new Point(getX(), getY());
	}

	private static Point getTopLeftPoint(List<? extends Point> points) {
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

	private static Point getTopRightPoint(List<? extends Point> points) {
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

	private static Point getBottomLeftPoint(List<? extends Point> points) {
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

	private static Point pointFromOptional(Optional<? extends Point> optionalPoint) {
		return optionalPoint.orElseThrow(() -> new IllegalArgumentException("List of point was empty"));
	}

	public static double getMinX(List<? extends Point> points) {
		Point point = pointFromOptional(points.stream().sorted(Comparator.comparingDouble(Point::getX)).findFirst());
		return point.getX();
	}

	public static double getMaxX(List<? extends Point> points) {
		Point point = pointFromOptional(
				points.stream().sorted(Comparator.comparingDouble(Point::getX).reversed()).findFirst());
		return point.getX();
	}

	public static double getMinY(List<? extends Point> points) {
		Point point = pointFromOptional(points.stream().sorted(Comparator.comparingDouble(Point::getY)).findFirst());
		return point.getY();
	}

	public static double getMaxY(List<? extends Point> points) {
		Point point = pointFromOptional(
				points.stream().sorted(Comparator.comparingDouble(Point::getY).reversed()).findFirst());
		return point.getY();
	}

	private static Point getBottomRightPoint(List<? extends Point> points) {
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
		return (int) awtRectangle.getMaxX();
	}

	public int getMaxY() {
		return (int) awtRectangle.getMaxY();
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

	public void setLocation(int x, int y) {
		awtRectangle.setLocation(x, y);
	}

	public List<IntegerPrecisionPoint> getAllPoints() {
		ArrayList<IntegerPrecisionPoint> allPoints = new ArrayList<>();
		for (int xPointIt = getX(); xPointIt <= getMaxX(); xPointIt++) {
			for (int yPointIt = getY(); yPointIt <= getMaxY(); yPointIt++) {
				allPoints.add(new IntegerPrecisionPoint(xPointIt, yPointIt));
			}
		}

		return allPoints;
	}

	public Point getOneRandomPointAllowingSubRectangleToFit(int subRectangleWidth, int subRectangleHeight) {
		int minX = getX();
		int maxX = getX() + getWidth() - subRectangleWidth;

		int minY = getY();
		int maxY = getY() + getHeight() - subRectangleHeight;

		int chosenX = RandomIntegerGenerator.getRandomNumberUsingNextInt(minX, maxX);
		int chosenY = RandomIntegerGenerator.getRandomNumberUsingNextInt(minY, maxY);

		return new Point(chosenX, chosenY);

	}

	public boolean contains(Point p) {
		return awtRectangle.contains(p);
	}

	@Override
	public String toString() {
		return awtRectangle.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(awtRectangle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntegerPrecisionRectangle other = (IntegerPrecisionRectangle) obj;
		return Objects.equals(awtRectangle, other.awtRectangle);
	}

}
