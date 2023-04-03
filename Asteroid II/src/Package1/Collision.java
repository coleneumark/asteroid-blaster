package Package1;

public class Collision {
	double x;
	double y;
	double width;
	double height;

	//Default Constructor
	public Collision() {
		this.setPosition(0, 0);
		this.setSize(1,1);
		}
	//Parametrised Constructor
	public Collision(double x, double y, double w, double h) {
		this.setPosition(x, y);
		this.setSize(w, h);
	}
	//Set Position
	public void setPosition (double x, double y) {
		this.x = x;
		this.y = y;
	}
	//Set Size
	public void setSize (double w, double h) {
		this.width = w;
		this.height = h;
	}
	//Check if Overlap (
	public boolean overlap (Collision other) {
		boolean noOverlap = this.x + this.width/2 < other.x ||
							other.x + other.width < this.x ||
							this.y + this.height/2 < other.y ||
							other.y +other.height < this.y;
		return !noOverlap;
	}
}
