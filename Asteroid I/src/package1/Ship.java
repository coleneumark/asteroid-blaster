package package1;

import javafx.scene.shape.Polygon;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Ship extends SpaceObject {

    public Ship(int x, int y) {
       super(new Polygon(-10,-15,-15,-25,-20,-25,-20,-20,-30,-20,-30,-25,-35,-25,-40,-15,-40,-30,-30,-30,-25,-45,-20,-30,-10,-30),x,y);
    }
}