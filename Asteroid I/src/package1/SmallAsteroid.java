package package1;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class SmallAsteroid extends SpaceObject {
	static double[] saArray = {2,14,4,6,8,4,14,8,14,12,10,18,8,20,6,18,4,14};
    public SmallAsteroid (int x, int y) {
    	super(new Polygon(saArray),x,y);
    }
}