package package1;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Bullet extends SpaceObject {

    public Bullet (int x, int y) {
       super(new Polygon(2,2,8,2,8,4,2,4),x,y);
    }
}