package package1;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class PlayerShip extends SpaceObject {
	//static double[] psArray = {30,25,35,25,40,15,40,30,30,30,25,45,20,30,10,30,10,15,15,25,20,25,20,20,30,20};

	//static double[] psArray = {-10,-15,-15,-25,-20,-25,-20,-20,-30,-20,-30,-25,-35,-25,-40,-15,-40,-30,-30,-30,-25,-45,-20,-30,-10,-30};
	static double[] psArray = {-5, -5, 10, 0, -5, 5};
    public PlayerShip(int x, int y) {
       super(new Polygon(psArray),x,y);
    }
}