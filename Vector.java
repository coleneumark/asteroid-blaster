package Application;

public class Vector {
	
	// We add all info to be stored here:
	// example of coordinates (points) lat, long, yarra yarra
	// should we make it private?
	public double x;
	public double y;
	
	// Create Constructor
	// Default Constructor sets both values to zero
	// No args --> zero vector :/
	public Vector() {
		// Similar to what we did in Python, set points to 0
		this.set(0, 0);
		
	}
	
	// Creating a parameterized constructor:
	public Vector(double x, double y) {
		this.set(x, y);
	}
	
	// set coordinates to reduce headache of additional implementation
	public void set(double x, double y) {
		this.x  = x;
		this.y = y;
	}
	
	public void add(double dx, double dy) {
		this.x += dx;
		this.y += dy;
	}
	
	public void multiply(double m) {
		// Scaling the vector (shrinking or stretching, you name it)
		this.x *= m;
		this.y *= m;
	}
	
	// retrieve length of the vector
	public double getLength() {
		// We did something similar for the one Python OOP project
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
	// Set vector to particular length
	// void because there is no return, we merely set the length of our "Vector", generic name for now
	public void setLenght(double L) {
		// new length as input
		// Vector might already have length not equal to "L"
		// use multiply function to change length of vector to length of 1
		// then we can successfully scale the shape to the desired length
		// find current length of vector:
		double currentLength = this.getLength();
		
		// error checking to prevent zero division error :/
		if (currentLength == 0) return;
		// scale vector to have a length of 1
		this.multiply(1/currentLength);
		// now we can successfully scale the shape (vector) to the length of the input
		this.multiply(L);
	}
	
	// handling the angle (edgy ( ͡° ͜ʖ ͡°))
	// define getter and setter method
	public double getAngle() {
		// we also want return in Degree, added consistency to correlate with setAngle method
		return Math.toDegrees(Math.atan2(this.y, this.x)); // little bit of trigonometry goes a long way :/
		
	}
	
	public void setAngle(double angleDegrees) {
		// need to know dem basic trigonometry
		// set angle vector makes with x-axis
		// creating right triangle for shape
		// tan0 = y/x
		// 0 = atan(y/x) ...eh...and so on and so forth...quick maff 
		// sin0 = y/L; y=L.sin0; cos0=x/L --> x=L.cos0
		// 1. Calculate the length
		double L = this.getLength();
		// angles need to be in Radians, necessary for Math sin and cosine functions (input args)
		double angleRadians = Math.toRadians(angleDegrees);
		// determine sine and cosine, used to set x and y
		this.x = L * Math.cos(angleRadians); // now, after conversion, can effectively input/plug into cos function
		this.y = L * Math.sin(angleRadians); // do the same for y point
		
	}

}
