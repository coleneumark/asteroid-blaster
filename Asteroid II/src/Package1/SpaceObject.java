package Package1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Polygon;

public class SpaceObject {

	private static final double WIDTH = 0;
	private static final double HEIGHT = 0;
	public Movement position;
	public Movement speed;
	public double turn;
	public Collision edge;
	//public Polygon polygon;
	public Image image;
	
	//Default constructor
	public SpaceObject() {
		this.position = new Movement();
		this.speed = new Movement();
		this.turn = 0;
		this.edge = new Collision();
	}
	//Parameterised Constructor
	public SpaceObject (String objectFile) {
		this();
		setObject(objectFile);
	}
	//Set Object
	public void setObject (String objectFile) {
		this.image = new Image(objectFile);
		this.edge.setSize(this.image.getWidth(), this.image.getHeight());
	}
	//Gets the edge of the object 
	public Collision getEdge() {
		this.edge.setPosition(this.position.x,this.position.y);
		return this.edge;
	}
	public boolean overlaps(SpaceObject other) {
		return this.getEdge().overlap(other.getEdge());
	}
	
	//Method to display the object at its center point (method does it in reverse order of code below)
	public void display (GraphicsContext gContext) {
		gContext.save();
		gContext.translate(this.position.x, this.position.y);
		gContext.rotate(this.turn);
		gContext.translate(-this.image.getWidth()/2,-this.image.getHeight()/2);
		gContext.drawImage(this.image, 0,0);
		gContext.restore();
	}
	
	//Method for object to reappear if it goes off screen
	public void screenreturn (double screenWidth, double screenHeight) {
		if (this.position.x + this.image.getWidth()/2 < 0)
			this.position.x = screenWidth;
		if (this.position.x > screenWidth )
			this.position.x = 0-this.image.getWidth()/2;
		if (this.position.y + this.image.getHeight()/2< 0)
			this.position.y = screenHeight;
		if (this.position.y > screenHeight )
			this.position.y = 0-this.image.getHeight()/2;
	}
	//Update Position based on Animator time and if object goes off screen
	public void update(double timePass) {
		this.position.add(this.speed.x*timePass,this.speed.y*timePass);
		this.screenreturn(800, 600);
		
	}
	
}
