package package1;

public class Move {
	public double x;
	public double y;
	
	public Move () {
		this.set(0,0);
	}
	public Move(double x, double y) {
		this.set(x, y);
	}
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void add(double dx, double dy) {
		this.x += dx;
		this.y += dy;
	}
	
	public void multiply(double m) {
		this.x *= m;
		this.y *= m;
	}
	
	public double getLength () {
		return Math.sqrt(this.x*this.x + this.y*this.y);
	}
	
	public void setLength(double L) {
		double currentLength = this.getLength();
		if (currentLength == 0)
			return;
		this.multiply(1/currentLength);
		this.multiply(L);
	}
	
	public double getAngle() {
		return Math.toRadians(Math.atan2(this.x,this.y));
	}
	
	public void setAngle (double angleDeg) {
		double L = this.getLength();
		double angleRad = Math.toRadians(angleDeg);
		this.x = L * Math.cos(angleRad);
		this.y = L * Math.sin(angleRad);
	}
}
