package package1;

import javafx.scene.shape.Polygon;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class EnemyShip extends SpaceObject {

    public EnemyShip(int x, int y) {
       super(new Polygon(4,12,8,8,10,2,12,8,14,2,16,8,20,12,16,16,14,22,12,16,10,22,8,16),x,y);
    }
}