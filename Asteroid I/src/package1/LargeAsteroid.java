package package1;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

import java.util.Random;

public class LargeAsteroid extends SpaceObject {
	
	public double rotateAsteroid; 
	//double scale = 5; 
	static double[] laArray = {10,70,20,30,40,20,70,40,70,60,50,90,40,100,30,90,20,70};
	
	public LargeAsteroid (int x, int y) {
    	super(new Polygon(laArray),x,y);
    	
    	//Generate a random number
    	Random randomNum = new Random();
    	
    	//Random Rotation
    	super.getSpaceObject().setRotate(randomNum.nextInt(360));
    	
    	//Random Acceleration
    	int randomAcc = 1 + randomNum.nextInt(20);
    	for (int i=0; i<randomAcc;i++) {
    		accelerate();
    	}
    	//Rotation of Asteroid based some number between 0.5 and -0.5
    	this.rotateAsteroid = 0.5 - randomNum.nextDouble();
    }
	@Override
	public void move( ) {
		super.move();
		super.getSpaceObject().setRotate(super.getSpaceObject().getRotate()+rotateAsteroid);
	}
}