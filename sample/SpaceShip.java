package sample;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.shape.Polygon;

public class SpaceShip extends HeavenlyBody {
	
	private ArrayList <HeavenlyBody> heavenlyBodies;

    SpaceShip(ArrayList <HeavenlyBody> heavenlyBodies) {
        super(new Polygon(-5,-5,10,0,-5,5), 150,100);
        this.heavenlyBodies = heavenlyBodies;
    }

    public void move() {

        double changeX = Math.cos(Math.toRadians(this.getShapeHeavenlyBody().getRotate()));
        double changeY = Math.sin(Math.toRadians(this.getShapeHeavenlyBody().getRotate()));

        this.getShapeHeavenlyBody().setTranslateX(getShapeHeavenlyBody().getTranslateX() + changeX);
        this.getShapeHeavenlyBody().setTranslateY(this.getShapeHeavenlyBody().getTranslateY() + changeY);

        if (this.getShapeHeavenlyBody().getTranslateX() < 0) {
            this.getShapeHeavenlyBody().setTranslateX(1);
        }

        if (this.getShapeHeavenlyBody().getTranslateX() > Main.WIDTH) {
            this.getShapeHeavenlyBody().setTranslateX(299);
        }

        if (this.getShapeHeavenlyBody().getTranslateY() < 0) {
            this.getShapeHeavenlyBody().setTranslateY(1);
        }

        if (this.getShapeHeavenlyBody().getTranslateY() > Main.HEIGHT) {
            this.getShapeHeavenlyBody().setTranslateY(199);
        }
    }
    
    
    public void hyperJump()	{
    	Random rnd = new Random();
    	boolean collisionDetected = true;
    	double x = 0;
    	double y = 0;
    	
    	while(collisionDetected) {
    		x = rnd.nextDouble() * Main.WIDTH;
    		y = rnd.nextDouble() * Main.HEIGHT;
    		
    		this.getShapeHeavenlyBody().setTranslateX(x);
    		this.getShapeHeavenlyBody().setTranslateY(y);
    		
    		collisionDetected = false;
    		for (HeavenlyBody body: heavenlyBodies) {
    			if (this.collide(body.getShapeHeavenlyBody())) {
    				collisionDetected = true;
    				break;
    			}
    		}
    	}
    	
    }
}
