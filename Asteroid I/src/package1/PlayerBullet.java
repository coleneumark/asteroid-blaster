package package1;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Random;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class PlayerBullet extends SpaceObject {
	static double[] bullArray = {1,1,4,1,4,2,1,2};
    
	public PlayerBullet (int x, int y) {
		super(new Polygon(bullArray),x,y);
    }
	public void move() {
		//Calculates the distance based on x,y coordinates and angle of rotation 
        double changeX = Math.cos(Math.toRadians(this.getSpaceObject().getRotate()));
        double changeY = Math.sin(Math.toRadians(this.getSpaceObject().getRotate()));
        //increase the amount of movement by a factor
        changeX *= 2.5;
        changeY *= 2.5;
        //Adds distance to movement
        this.getSpaceObject().setTranslateX(this.getSpaceObject().getTranslateX() + changeX);
        this.getSpaceObject().setTranslateY(this.getSpaceObject().getTranslateY() + changeY);
    }
}