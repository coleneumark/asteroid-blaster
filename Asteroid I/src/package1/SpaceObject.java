package package1;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class SpaceObject {

    private Polygon object;
    private Point2D movement;
    
    //Build the Space Object and set its initial coordinates
    public SpaceObject(Polygon polygon, int x, int y) {
        this.object = polygon;
        this.object.setTranslateX(x);
        this.object.setTranslateY(y);

    //Create (x,y) point to facilitate movement 
        this.movement = new Point2D(0, 0);
    }
    
    //Methods to move left and right a certain number of degrees
    int degrees = 2;
    public void turnLeft() {
        this.object.setRotate(this.object.getRotate() - degrees);
    }
    public void turnRight() {
        this.object.setRotate(this.object.getRotate() + degrees);
    }
    
    //Method to move object based on initial start point
    public void move() {
        this.object.setTranslateX(this.object.getTranslateX() + this.movement.getX());
        this.object.setTranslateY(this.object.getTranslateY() + this.movement.getY());
  
    //Return Space Object to screen if it goes off screen
    if (this.object.getTranslateX() < 0) {
        this.object.setTranslateX(this.object.getTranslateX() + Asteroid.WIDTH);
    }

    if (this.object.getTranslateX() > Asteroid.WIDTH) {
        this.object.setTranslateX(this.object.getTranslateX() % Asteroid.WIDTH);
    }

    if (this.object.getTranslateY() < 0) {
        this.object.setTranslateY(this.object.getTranslateY() + Asteroid.HEIGHT);
    }

    if (this.object.getTranslateY() > Asteroid.HEIGHT) {
        this.object.setTranslateY(this.object.getTranslateY() % Asteroid.HEIGHT);
    }
    }
    
    
    //Method to accelerate Space Object
	public void accelerate() {
		//Calculates the distance based on x,y coordinates and angle of rotation 
        double changeX = Math.cos(Math.toRadians(this.object.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.object.getRotate()));
        changeX *= 0.025;
        changeY *= 0.025;
        //Adds distance to movement
        this.movement = this.movement.add(changeX, changeY);
    }
	
	public boolean collide(SpaceObject other) {
        Shape collisionArea = Shape.intersect(this.object, other.getSpaceObject());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
	
	public void setMovement(Point2D m){
	 this.movement = m;      }
	
	public Point2D getMovement(){
	return this.movement;
	}
	
	
	//Method to return 
	public Polygon getSpaceObject() {
        return this.object;
    }
}

