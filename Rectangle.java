package Application;

public class Rectangle {
	// Adding the top-left corner (represented as a point --> (x,y)) of the rectangle
	// storing measurements:
	double x;
	double y;
	double width;
	double height;
	
	// Creating Constructors and Setters
	
	// Default Constructor
	public Rectangle() {
		this.setPosition(0, 0);
		this.setSize(1, 1); // teeny tiny rectangle
	}
	
	// Parameterized Constructor (takes input/arguments)
	public Rectangle(double x, double y, double width, double height) {
		// use specified set functions (see below) to set position and size:
		this.setPosition(x, y);
		this.setSize(width, height);
	}
	
	// Set functions
	// Setting the size and the positions
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	// specify width and height to set size
	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	// need to check for any overlaps with other "Rectangles"
	// Looks at separating axis, which will help determine if two rectangles overlap
	// (must be able to draw a line (the axis) which separates the objects), we look at 4 use cases:
	// 1) self is to the left of other
	// 2) self is to the right of other
	// 3) self is below other
	// 4) self is above other
	// check each case by comparing the coordinates of two of the edges, e.g.
	// case 1 (refer to above use-cases), we can determine if self is to left of other, because the right edge (x + w)
	// of self is less than left edge of other object (x) (rectangle)
	// we need to compare each of the four cases
	// COLLISION DETECTION!!!
	public boolean overlaps(Rectangle other) {
		// 4 cases where there is no overlap
		// 1 --> flag if there are no overlaps:
		// 2 --> this is to the left of other
		// 3 --> this to the right of other
		// 4 --> this is above other
		// other rectangle is above this (self)
		// might also have case where both are above and completely to the right :/
		boolean noOverlap = 
				this.x + this.width < other.x || 
				// check if this (self) is to the right of other
				other.x + other.width < this.x ||
				// look at  bottom edge:
				this.y + this.height < other.y || // bottom edge of this (self) rectangle is above the top of other rectangle
				other.y + other.height < this.y;
		// only interested in checking whether there is overlap, so return based on four specified conditions
		return !noOverlap; // (return inverted) to return whether two rectangles overlap
	}

}
