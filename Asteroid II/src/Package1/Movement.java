package Package1;

public class Movement {
	
	public double x;
	public double y;
	
	//Default Constructor
	public Movement() {
		this.set(0, 0);
	}
	//Parametised Constructor
	public Movement (double x, double y) {
		this.set(x, y);
	}
	//Set X and Y
	public void set (double x, double y) {
		this.x = x;
		this.y =y;
	}
	//Get X and Y
		//public double get() {
			//return (this.x, this.y);
		//}
	//Add to X and Y
	public void add(double dx, double dy) {
		this.x += dx;
		this.y += dy;
	}
	//Multiple X and Y
	public void multiply(double m) {
		this.x *= m;
		this.y *= m;
	}
	//Get length of Movement
	public double getLength () {
		return Math.sqrt(this.x*this.x + this.y*this.y);
	}
	//Set length of Movement
	public void setLength(double L) {
		double currentLength = this.getLength();
		if (currentLength == 0) {
			this.set(L, 0);
		} else 
		{
		this.multiply(1/currentLength);
		this.multiply(L);
		}
	}
	//Get Angle of Movement in degrees
	public double getAngle() {
		return Math.toRadians(Math.atan2(this.x,this.y));
	}
	//Set Angle of Movement in degrees
	public void setAngle (double angleDeg) {
		double L = this.getLength();
		double angleRad = Math.toRadians(angleDeg);
		this.x = L * Math.cos(angleRad);
		this.y = L * Math.sin(angleRad);
	}
}
